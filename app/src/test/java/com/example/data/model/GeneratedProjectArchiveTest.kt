package com.example.data.model

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.zip.ZipInputStream
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class GeneratedProjectArchiveTest {
    @Test
    fun writesFilesToZipWithTheirRelativePaths() {
        val output = ByteArrayOutputStream()
        GeneratedProjectArchive.write(
            listOf(
                GeneratedProjectFile("README.md", "Starter project"),
                GeneratedProjectFile("src/main.kt", "fun main() = Unit")
            ),
            output
        )
        val entries = mutableMapOf<String, String>()
        ZipInputStream(ByteArrayInputStream(output.toByteArray())).use { zip ->
            while (true) {
                val entry = zip.nextEntry ?: break
                entries[entry.name] = zip.readBytes().toString(Charsets.UTF_8)
            }
        }
        assertEquals("Starter project", entries["README.md"])
        assertEquals("fun main() = Unit", entries["src/main.kt"])
    }

    @Test
    fun rejectsUnsafeAndDuplicatePaths() {
        assertThrows(IllegalArgumentException::class.java) {
            GeneratedProjectArchive.write(
                listOf(GeneratedProjectFile("../escape.txt", "x")),
                ByteArrayOutputStream()
            )
        }
        assertThrows(IllegalArgumentException::class.java) {
            GeneratedProjectArchive.write(
                listOf(
                    GeneratedProjectFile("src/App.kt", "a"),
                    GeneratedProjectFile("src/app.kt", "b")
                ),
                ByteArrayOutputStream()
            )
        }
    }

    @Test
    fun rejectsOversizedFilesAndEmptyProjects() {
        assertThrows(IllegalArgumentException::class.java) {
            GeneratedProjectArchive.write(emptyList(), ByteArrayOutputStream())
        }
        assertThrows(IllegalArgumentException::class.java) {
            GeneratedProjectArchive.write(
                listOf(GeneratedProjectFile("large.txt", "x".repeat(16 * 1024 + 1))),
                ByteArrayOutputStream()
            )
        }
    }
}
