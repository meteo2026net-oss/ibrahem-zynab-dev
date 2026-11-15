#!/usr/bin/env python3
"""Extract text, tables, and embedded-image references from the approved DOCX."""
from __future__ import annotations

import argparse
import hashlib
import json
import mimetypes
import posixpath
import zipfile
from pathlib import Path
from xml.etree import ElementTree as ET

NS = {
    "w": "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
    "a": "http://schemas.openxmlformats.org/drawingml/2006/main",
    "wp": "http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing",
    "r": "http://schemas.openxmlformats.org/officeDocument/2006/relationships",
    "pr": "http://schemas.openxmlformats.org/package/2006/relationships",
}
W = "{" + NS["w"] + "}"


def paragraph_text(node: ET.Element) -> str:
    parts: list[str] = []
    for child in node.iter():
        if child.tag == W + "t":
            parts.append(child.text or "")
        elif child.tag == W + "tab":
            parts.append("\t")
        elif child.tag in (W + "br", W + "cr"):
            parts.append("\n")
    return "".join(parts).strip()


def render_table(table: ET.Element, depth: int = 0) -> list[str]:
    lines: list[str] = []
    prefix = "  " * depth
    for row in table.findall("./w:tr", NS):
        cells: list[str] = []
        for cell in row.findall("./w:tc", NS):
            cell_parts: list[str] = []
            for child in list(cell):
                if child.tag == W + "p":
                    value = paragraph_text(child)
                    if value:
                        cell_parts.append(value)
                elif child.tag == W + "tbl":
                    nested = render_table(child, depth + 1)
                    if nested:
                        cell_parts.append(" / ".join(x.strip() for x in nested))
            cells.append(" ; ".join(cell_parts).replace("\n", " "))
        lines.append(prefix + "| " + " | ".join(cells) + " |")
    return lines


def extract_part(data: bytes) -> list[str]:
    root = ET.fromstring(data)
    body = root.find(".//w:body", NS)
    container = body if body is not None else root
    lines: list[str] = []
    for child in list(container):
        if child.tag == W + "p":
            value = paragraph_text(child)
            if value:
                lines.append(value)
        elif child.tag == W + "tbl":
            lines.extend(render_table(child))
    return lines


def build_image_index(document_xml: bytes, relationships_xml: bytes) -> list[dict]:
    root = ET.fromstring(document_xml)
    relroot = ET.fromstring(relationships_xml)
    rels = {item.attrib["Id"]: item.attrib["Target"] for item in relroot}
    body = root.find(".//w:body", NS)
    if body is None:
        return []
    occurrences: list[dict] = []
    paragraph_index = 0
    for paragraph in body.findall(".//w:p", NS):
        paragraph_index += 1
        text = paragraph_text(paragraph)
        blips = paragraph.findall(".//a:blip", NS)
        docprs = paragraph.findall(".//wp:docPr", NS)
        for image_index, blip in enumerate(blips):
            rid = blip.attrib.get("{" + NS["r"] + "}embed")
            target = rels.get(rid or "")
            if not target:
                continue
            media_path = posixpath.normpath(posixpath.join("word", target))
            docpr = docprs[image_index] if image_index < len(docprs) else None
            occurrences.append({
                "occurrence": len(occurrences) + 1,
                "paragraph_index": paragraph_index,
                "media_path": media_path,
                "relationship_id": rid,
                "alt_text": docpr.attrib.get("descr") if docpr is not None else None,
                "title": docpr.attrib.get("title") if docpr is not None else None,
                "adjacent_paragraph_text": text or None,
            })
    return occurrences


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--input", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    args = parser.parse_args()
    source = args.input
    if not source.is_file():
        raise SystemExit(f"Source DOCX not found: {source}")

    source_bytes = source.read_bytes()
    source_size = len(source_bytes)
    sha256 = hashlib.sha256(source_bytes).hexdigest()
    git_blob_sha1 = hashlib.sha1(
        f"blob {source_size}\0".encode("ascii") + source_bytes
    ).hexdigest()

    with zipfile.ZipFile(source) as archive:
        names = set(archive.namelist())
        if "word/document.xml" not in names:
            raise SystemExit("Invalid DOCX: word/document.xml is missing")
        document_xml = archive.read("word/document.xml")
        relationships_path = "word/_rels/document.xml.rels"
        relationships_xml = archive.read(relationships_path) if relationships_path in names else b"<Relationships/>"

        sections: list[str] = []
        ordered_parts = ["word/document.xml"]
        ordered_parts += sorted(
            n for n in names
            if n.startswith("word/")
            and (
                n.startswith("word/header")
                or n.startswith("word/footer")
                or n in {"word/footnotes.xml", "word/endnotes.xml"}
            )
            and n.endswith(".xml")
        )
        for part in ordered_parts:
            title = "DOCUMENT BODY" if part == "word/document.xml" else part.upper()
            try:
                part_lines = extract_part(document_xml if part == "word/document.xml" else archive.read(part))
            except ET.ParseError as exc:
                raise SystemExit(f"Could not parse {part}: {exc}") from exc
            if part_lines:
                sections.append(f"\n\n===== {title} =====\n")
                sections.extend(part_lines)

        media = []
        for info in sorted(archive.infolist(), key=lambda x: x.filename):
            if info.is_dir() or not info.filename.startswith("word/media/"):
                continue
            data = archive.read(info.filename)
            media.append({
                "path": info.filename,
                "size_bytes": len(data),
                "sha256": hashlib.sha256(data).hexdigest(),
                "mime_type": mimetypes.guess_type(info.filename)[0] or "application/octet-stream",
            })
        image_occurrences = build_image_index(document_xml, relationships_xml)

    args.output.parent.mkdir(parents=True, exist_ok=True)
    header = [
        "# Extracted text from the approved master specification",
        "",
        f"- Source file: {source.as_posix()}",
        f"- Source size (bytes): {source_size}",
        f"- Source SHA-256: {sha256}",
        f"- Git blob SHA-1: {git_blob_sha1}",
        f"- Embedded media files: {len(media)}",
        f"- Embedded image occurrences in document body: {len(image_occurrences)}",
        "- Extraction: DOCX XML text and table-cell content; visual layout is not reproduced.",
        "- Original source: unchanged.",
        "",
    ]
    args.output.write_text("\n".join(header + sections) + "\n", encoding="utf-8")

    base = args.output.with_suffix("")
    metadata = {
        "source": source.as_posix(),
        "source_size_bytes": source_size,
        "source_sha256": sha256,
        "git_blob_sha1": git_blob_sha1,
        "output": args.output.as_posix(),
        "output_size_bytes": args.output.stat().st_size,
        "embedded_media_count": len(media),
        "embedded_image_occurrences": len(image_occurrences),
        "extraction_method": "python-stdlib-zipfile-xml",
        "source_modified": False,
    }
    base.with_suffix(".metadata.json").write_text(
        json.dumps(metadata, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    base.with_suffix(".media-index.json").write_text(
        json.dumps(media, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    base.with_suffix(".image-index.json").write_text(
        json.dumps(image_occurrences, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    print(json.dumps(metadata, ensure_ascii=False))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
