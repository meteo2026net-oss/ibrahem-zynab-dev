package com.example.data.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GeneratedProjectFileTest {
    @Test
    fun acceptsSafeRelativeTextFile() {
        val file = GeneratedProjectFile.fromMap(
            mapOf("path" to "src/main.kt", "content" to "fun main() = Unit")
        )
        assertEquals("src/main.kt", file?.path)
    }

    @Test
    fun rejectsTraversalAndAbsolutePaths() {
        listOf("../outside.txt", "/root.txt", "src\\\\main.kt", ".github/workflows/build.yml")
            .forEach { path ->
                assertNull(GeneratedProjectFile.fromMap(mapOf("path" to path, "content" to "x")))
            }
    }

    @Test
    fun rejectsCredentialFilesAndOversizedContent() {
        assertNull(GeneratedProjectFile.fromMap(mapOf("path" to ".env", "content" to "secret")))
        assertNull(GeneratedProjectFile.fromMap(mapOf("path" to "large.txt", "content" to "x".repeat(16 * 1024 + 1))))
    }

    @Test
    fun rejectsMalformedEntries() {
        assertNull(GeneratedProjectFile.fromMap("not a map"))
        assertNull(GeneratedProjectFile.fromMap(mapOf("path" to "src/a.kt")))
    }
}
