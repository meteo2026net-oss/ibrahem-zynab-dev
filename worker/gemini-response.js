"use strict";

/**
 * Extract text from the GenerateContentResponse returned directly by
 * @google/genai models.generateContent().
 */
function extractText(response) {
  if (typeof response?.text === "string" && response.text.trim()) {
    return response.text.trim();
  }
  const candidates = response && response.candidates;
  if (!Array.isArray(candidates) || !candidates.length) {
    throw new Error("The model returned no candidates.");
  }
  const parts = candidates[0].content && candidates[0].content.parts;
  if (!Array.isArray(parts)) throw new Error("The model response has no content parts.");
  const text = parts.map(part => typeof part.text === "string" ? part.text : "").join("").trim();
  if (!text) throw new Error("The model returned no text content.");
  return text;
}

module.exports = { extractText };
