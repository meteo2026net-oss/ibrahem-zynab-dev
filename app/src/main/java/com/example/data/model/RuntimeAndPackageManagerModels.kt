package com.example.data.model

import java.util.UUID

enum class ProgrammingLanguage(val titleAr: String, val packageManager: String) {
    JAVASCRIPT("JavaScript / TypeScript", "npm / yarn"),
    PYTHON("Python", "pip / uv"),
    FLUTTER_DART("Flutter / Dart", "pub"),
    DOTNET_CSHARP("C# / .NET", "NuGet"),
    KOTLIN_JAVA("Kotlin / Java", "Gradle / Maven"),
    RUST("Rust", "Cargo"),
    GO("Go", "Go Modules"),
    PHP("PHP", "Composer")
}

data class InstalledPackage(
    val id: String = UUID.randomUUID().toString(),
    val projectId: String = "",
    val packageName: String,
    val version: String,
    val language: ProgrammingLanguage,
    val isSecure: Boolean = true,
    val license: String = "MIT",
    val installedAt: Long = System.currentTimeMillis()
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "projectId" to projectId,
        "packageName" to packageName,
        "version" to version,
        "language" to language.name,
        "isSecure" to isSecure,
        "license" to license,
        "installedAt" to installedAt
    )
}

data class ProjectRuntimeState(
    val projectId: String = "",
    val isRunning: Boolean = false,
    val runtimeMode: String = "Cloud Sandbox", // Local Runtime or Cloud Runtime
    val cpuUsagePercent: Int = 14,
    val memoryUsageMb: Int = 185,
    val activePort: Int = 8080,
    val executionLogs: List<String> = listOf("Runtime initialized securely in isolated sandbox container.")
)
