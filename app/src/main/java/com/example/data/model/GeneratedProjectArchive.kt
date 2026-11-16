package com.example.data.model

import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Writes generated project text files into a ZIP archive.
 * Paths and sizes are revalidated at export time; files are never executed.
 */
object GeneratedProjectArchive {
    fun write(files: List<GeneratedProjectFile>, output: OutputStream) {
        require(files.isNotEmpty() && files.size <= 40) { "Project archive must contain 1 to 40 files." }
        val seen = HashSet<String>()
        val blockedNames = setOf(".env", ".env.local", ".env.production", "id_rsa", "id_ed25519", "credentials.json", "service-account.json")
        var totalBytes = 0
        ZipOutputStream(output, Charsets.UTF_8).use { zip ->
            files.forEach { file ->
                val path = file.path
                val segments = path.split("/")
                require(path.isNotBlank() && path.length <= 240 && !path.startsWith("/") && !path.contains("\\") &&
                    segments.all { it.matches(Regex("^[A-Za-z0-9][A-Za-z0-9._-]{0,79}$")) && !it.startsWith(".") && !blockedNames.contains(it.lowercase()) }) {
                    "Unsafe project file path."
                }
                require(seen.add(path.lowercase())) { "Duplicate project file path." }
                val bytes = file.content.toByteArray(Charsets.UTF_8)
                require(bytes.size <= 16 * 1024) { "Project file exceeds 16 KiB." }
                totalBytes += bytes.size
                require(totalBytes <= 160 * 1024) { "Project archive exceeds 160 KiB." }
                zip.putNextEntry(ZipEntry(path).apply { time = 0L })
                zip.write(bytes)
                zip.closeEntry()
            }
        }
    }
}
