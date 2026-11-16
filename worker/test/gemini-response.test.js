"use strict";

const test = require("node:test");
const assert = require("node:assert/strict");
const { extractText } = require("../gemini-response");

test("extracts the SDK convenience text field", () => {
  assert.equal(extractText({ text: "  generated output  " }), "generated output");
});

test("extracts text from candidate parts when convenience text is absent", () => {
  assert.equal(extractText({
    candidates: [{ content: { parts: [{ text: "first " }, { text: "second" }] } }]
  }), "first second");
});

test("rejects a response with no candidates", () => {
  assert.throws(() => extractText({}), /no candidates/);
});

test("rejects candidates with no text content", () => {
  assert.throws(() => extractText({
    candidates: [{ content: { parts: [{ functionCall: { name: "tool" } }] } }]
  }), /no text content/);
});
