"use strict";

const MAX_FILES = 40;
const MAX_FILE_BYTES = 16 * 1024;
const MAX_TOTAL_BYTES = 160 * 1024;
const PATH_SEGMENT = /^[A-Za-z0-9][A-Za-z0-9._-]{0,79}$/;
const SECRET_PATTERNS = [
  /-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----/,
  /\\bAIza[0-9A-Za-z_-]{30,}\\b/,
  /\\bgh[pousr]_[A-Za-z0-9]{20,}\\b/,
  /\\bsk-[A-Za-z0-9]{24,}\\b/,
  /\\bAKIA[0-9A-Z]{16}\\b/
];
const BLOCKED_NAMES = new Set([
  ".env", ".env.local", ".env.production", "id_rsa", "id_ed25519",
  "credentials.json", "service-account.json"
]);

function validateProjectArtifacts(value) {
  if (!value || typeof value !== "object" || Array.isArray(value)) {
    throw new Error("Generated project response must be a JSON object.");
  }
  const summary = typeof value.summary === "string" ? value.summary.trim() : "";
  if (!summary || summary.length > 4000) {
    throw new Error("Generated project summary is missing or too long.");
  }
  if (!Array.isArray(value.files) || value.files.length === 0 || value.files.length > MAX_FILES) {
    throw new Error("Generated project must contain between 1 and 40 files.");
  }

  const seen = new Set();
  let totalBytes = 0;
  const files = value.files.map((file, index) => {
    if (!file || typeof file !== "object" || Array.isArray(file)) {
      throw new Error("Invalid generated file at index " + index + ".");
    }
    if (typeof file.path !== "string" || typeof file.content !== "string") {
      throw new Error("Generated file path and content must be strings.");
    }
    const path = file.path.trim();
    const segments = path.split("/");
    if (!path || path.length > 240 || path.startsWith("/") || path.includes("\\") ||
        segments.some(segment => !PATH_SEGMENT.test(segment) || segment === "." || segment === "..") ||
        segments.some(segment => BLOCKED_NAMES.has(segment.toLowerCase())) ||
        segments.some(segment => segment.startsWith("."))) {
      throw new Error("Unsafe generated file path: " + path.slice(0, 80));
    }
    const normalized = path.toLowerCase();
    if (seen.has(normalized)) throw new Error("Duplicate generated file path: " + path);
    seen.add(normalized);

    if (SECRET_PATTERNS.some(pattern => pattern.test(file.content))) {
      throw new Error("Generated file appears to contain a credential: " + path);
    }
    const bytes = Buffer.byteLength(file.content, "utf8");
    if (bytes > MAX_FILE_BYTES) throw new Error("Generated file exceeds 16 KiB: " + path);
    totalBytes += bytes;
    if (totalBytes > MAX_TOTAL_BYTES) throw new Error("Generated project exceeds 160 KiB.");
    return { path, content: file.content };
  });

  return { summary, files, totalBytes };
}

function parseProjectArtifacts(text) {
  if (typeof text !== "string" || !text.trim()) {
    throw new Error("The model returned an empty project response.");
  }
  let parsed;
  try {
    parsed = JSON.parse(text);
  } catch {
    throw new Error("The model response is not valid project JSON.");
  }
  return validateProjectArtifacts(parsed);
}

module.exports = { parseProjectArtifacts, validateProjectArtifacts };
