const test = require("node:test");
const assert = require("node:assert/strict");
const { sanitizeResultFiles } = require("../lib/project-artifacts.js");

test("accepts safe generated source files", () => {
  const files = sanitizeResultFiles([{ path: "src/main.ts", content: "export {};" }]);
  assert.deepEqual(files, [{ path: "src/main.ts", content: "export {};" }]);
});

test("rejects traversal, absolute, hidden and credential paths", () => {
  for (const path of ["../outside", "/root", ".github/workflows/build.yml", "config/.env", "id_rsa"]) {
    assert.throws(() => sanitizeResultFiles([{ path, content: "x" }]), /Unsafe generated file path/);
  }
});

test("rejects duplicate paths without case sensitivity", () => {
  assert.throws(() => sanitizeResultFiles([
    { path: "src/App.ts", content: "a" },
    { path: "src/app.ts", content: "b" },
  ]), /Duplicate generated file path/);
});

test("enforces file count and content size limits", () => {
  assert.throws(() => sanitizeResultFiles([]), /between 1 and 40/);
  assert.throws(() => sanitizeResultFiles(Array.from({ length: 41 }, (_, i) => ({
    path: "f" + i + ".txt", content: "x",
  }))), /between 1 and 40/);
  assert.throws(() => sanitizeResultFiles([{ path: "large.txt", content: "x".repeat(16 * 1024 + 1) }]), /exceeds 16 KiB/);
});

test("enforces aggregate project size limit", () => {
  const files = Array.from({ length: 11 }, (_, i) => ({
    path: "file" + i + ".txt", content: "x".repeat(15 * 1024),
  }));
  assert.throws(() => sanitizeResultFiles(files), /exceeds 160 KiB/);
});
