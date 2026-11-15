#!/usr/bin/env python3
"""Extract readable text and tables from the repository's approved DOCX source.

Uses only Python's standard library so the extraction is reproducible in CI.
The original DOCX is never modified.
"""
from __future__ import annotations

import argparse
import hashlib
import json
import zipfile
from pathlib import Path
from xml.etree import ElementTree as ET

NS = {
    "w": "http://schemas.openxmlformats.org/wordprocessingml/2006/main",
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


def extract_part(data: bytes, part_name: str) -> list[str]:
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
        elif child.tag == W + "sectPr":
            continue
    return lines


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--input", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    args = parser.parse_args()

    source = args.input
    if not source.is_file():
        raise SystemExit(f"Source DOCX not found: {source}")

    digest = hashlib.sha256(source.read_bytes()).hexdigest()
    with zipfile.ZipFile(source) as archive:
        names = set(archive.namelist())
        if "word/document.xml" not in names:
            raise SystemExit("Invalid DOCX: word/document.xml is missing")
        sections: list[str] = []
        ordered_parts = ["word/document.xml"]
        ordered_parts += sorted(
            n for n in names
            if n.startswith("word/") and
            (n.startswith("word/header") or n.startswith("word/footer") or
             n in {"word/footnotes.xml", "word/endnotes.xml"})
            and n.endswith(".xml")
        )
        for part in ordered_parts:
            title = "DOCUMENT BODY" if part == "word/document.xml" else part.upper()
            try:
                lines = extract_part(archive.read(part), part)
            except ET.ParseError as exc:
                raise SystemExit(f"Could not parse {part}: {exc}") from exc
            if lines:
                sections.append(f"\n\n===== {title} =====\n")
                sections.extend(lines)

    args.output.parent.mkdir(parents=True, exist_ok=True)
    header = [
        "# Extracted text from the approved master specification",
        "",
        f"- Source file: {source.as_posix()}",
        f"- Source size (bytes): {source.stat().st_size}",
        f"- Source SHA-256: {digest}",
        "- Extraction: DOCX XML text and table-cell content; visual layout is not reproduced.",
        "- Original source: unchanged.",
        "",
    ]
    args.output.write_text("\n".join(header + sections) + "\n", encoding="utf-8")
    metadata = {
        "source": source.as_posix(),
        "source_size_bytes": source.stat().st_size,
        "source_sha256": digest,
        "output": args.output.as_posix(),
        "output_size_bytes": args.output.stat().st_size,
        "extraction_method": "python-stdlib-zipfile-xml",
        "source_modified": False,
    }
    args.output.with_suffix(".metadata.json").write_text(
        json.dumps(metadata, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    print(json.dumps(metadata, ensure_ascii=False))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
