package com.example.data.model

/**
 * A bounded text file returned by the trusted project-generation worker.
 * File contents are data only; callers must not execute generated code automatically.
 */
data class GeneratedProjectFile(
    val path: String,
    val content: String
) {
    companion object {
        private val segmentPattern = Regex("^[A-Za-z0-9][A-Za-z0-9._-]{0,79}$")
        private val blockedNames = setOf(
            ".env", ".env.local", ".env.production", "id_rsa", "id_ed25519",
            "credentials.json", "service-account.json"
        )

        fun fromMap(raw: Any?): GeneratedProjectFile? {
            val map = raw as? Map<*, *> ?: return null
            val path = (map["path"] as? String)?.trim() ?: return null
            val content = map["content"] as? String ?: return null
            val segments = path.split("/")
            if (path.isBlank() || path.length > 240 || path.startsWith("/") || path.contains("\\") ||
                segments.any { !segmentPattern.matches(it) || it.startsWith(".") || blockedNames.contains(it.lowercase()) } ||
                content.toByteArray(Charsets.UTF_8).size > 16 * 1024
            ) return null
            return GeneratedProjectFile(path, content)
        }
    }
}
