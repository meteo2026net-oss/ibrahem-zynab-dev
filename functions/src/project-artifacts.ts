import { Buffer } from "node:buffer";
import { HttpsError } from "firebase-functions/v2/https";

export interface GeneratedProjectFile {
  path: string;
  content: string;
}

/** Validate untrusted worker output again at the persistence boundary. */
export function sanitizeResultFiles(value: unknown): GeneratedProjectFile[] {
  if (!Array.isArray(value) || value.length < 1 || value.length > 40) {
    throw new HttpsError("invalid-argument", "Generated result must contain between 1 and 40 files.");
  }
  const seen = new Set<string>();
  let totalBytes = 0;
  const secretPatterns = [
    /-----BEGIN (?:RSA |EC |OPENSSH )?PRIVATE KEY-----/,
    /\bAIza[0-9A-Za-z_-]{30,}\b/,
    /\bgh[pousr]_[A-Za-z0-9]{20,}\b/,
    /\bsk-[A-Za-z0-9]{24,}\b/,
    /\bAKIA[0-9A-Z]{16}\b/,
  ];
  const blocked = new Set([
    ".env", ".env.local", ".env.production", "id_rsa", "id_ed25519",
    "credentials.json", "service-account.json",
  ]);
  return value.map((entry: unknown) => {
    if (!entry || typeof entry !== "object") {
      throw new HttpsError("invalid-argument", "Invalid generated file entry.");
    }
    const file = entry as Record<string, unknown>;
    if (typeof file.path !== "string" || typeof file.content !== "string") {
      throw new HttpsError("invalid-argument", "Generated file path and content must be strings.");
    }
    const path = file.path.trim();
    const segments = path.split("/");
    if (!path || path.length > 240 || path.startsWith("/") || path.includes("\\") ||
        segments.some((part) => !/^[A-Za-z0-9][A-Za-z0-9._-]{0,79}$/.test(part)) ||
        segments.some((part) => part.startsWith(".") || blocked.has(part.toLowerCase()))) {
      throw new HttpsError("invalid-argument", "Unsafe generated file path.");
    }
    const key = path.toLowerCase();
    if (seen.has(key)) throw new HttpsError("invalid-argument", "Duplicate generated file path.");
    seen.add(key);
    if (secretPatterns.some((pattern) => pattern.test(file.content))) {
      throw new HttpsError("invalid-argument", "Generated file contains credential-like material.");
    }
    const bytes = Buffer.byteLength(file.content, "utf8");
    if (bytes > 16 * 1024) throw new HttpsError("invalid-argument", "Generated file exceeds 16 KiB.");
    totalBytes += bytes;
    if (totalBytes > 160 * 1024) throw new HttpsError("invalid-argument", "Generated project exceeds 160 KiB.");
    return { path, content: file.content };
  });
}
