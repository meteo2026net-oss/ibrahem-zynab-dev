"use strict";

const test = require("node:test");
const assert = require("node:assert/strict");
const { parseProjectArtifacts, validateProjectArtifacts } = require("../project-artifacts");

test("accepts a valid project manifest", () => {
  const result = parseProjectArtifacts(JSON.stringify({
    summary: "A small starter app",
    files: [{ path: "src/main.js", content: "console.log('hello');" }]
  }));
  assert.equal(result.files.length, 1);
  assert.equal(result.files[0].path, "src/main.js");
});

test("rejects invalid JSON and missing files", () => {
  assert.throws(() => parseProjectArtifacts("{"), /valid project JSON/);
  assert.throws(() => validateProjectArtifacts({ summary: "x", files: [] }), /between 1 and 40/);
});

test("rejects traversal, absolute, hidden, and secret paths", () => {
  for (const path of ["../outside.txt", "/root.txt", ".github/workflows/x.yml", "config/.env", "id_rsa"]) {
    assert.throws(() => validateProjectArtifacts({
      summary: "x", files: [{ path, content: "x" }]
    }), /Unsafe generated file path/);
  }
});

test("rejects duplicate paths case-insensitively", () => {
  assert.throws(() => validateProjectArtifacts({
    summary: "x",
    files: [
      { path: "src/App.js", content: "a" },
      { path: "src/app.js", content: "b" }
    ]
  }), /Duplicate generated file path/);
});

test("enforces per-file and total size limits", () => {
  assert.throws(() => validateProjectArtifacts({
    summary: "x", files: [{ path: "large.txt", content: "x".repeat(16 * 1024 + 1) }]
  }), /exceeds 16 KiB/);
  assert.throws(() => validateProjectArtifacts({
    summary: "x",
    files: Array.from({ length: 11 }, (_, i) => ({
      path: "f" + i + ".txt", content: "x".repeat(15 * 1024)
    }))
  }), /exceeds 160 KiB/);
});

test("rejects too many files and oversized summaries", () => {
  assert.throws(() => validateProjectArtifacts({
    summary: "x", files: Array.from({ length: 41 }, (_, i) => ({ path: "f" + i, content: "" }))
  }), /between 1 and 40/);
  assert.throws(() => validateProjectArtifacts({
    summary: "x".repeat(4001), files: [{ path: "a.txt", content: "" }]
  }), /summary is missing or too long/);
});

test("rejects common provider credentials embedded in generated content", () => {
  for (const content of [
    "AIza" + "A".repeat(35),
    "ghp_" + "a".repeat(30),
    "-----BEGIN PRIVATE KEY-----"
  ]) {
    assert.throws(() => validateProjectArtifacts({
      summary: "x", files: [{ path: "src/config.ts", content }]
    }), /credential/);
  }
});
