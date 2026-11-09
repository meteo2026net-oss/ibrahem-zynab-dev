package com.example.data.model

import java.util.UUID

data class CodeStudioFile(
    val id: String = UUID.randomUUID().toString(),
    val projectId: String = "",
    val filePath: String = "src/main/MainActivity.kt",
    val content: String = "// METRA Code Studio Workspace\npackage com.example\n\nfun main() {\n    println(\"METRA AI Control Studio\")\n}",
    val language: String = "kotlin",
    val isModified: Boolean = false,
    val lastSavedAt: Long = System.currentTimeMillis()
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "projectId" to projectId,
        "filePath" to filePath,
        "content" to content,
        "language" to language,
        "isModified" to isModified,
        "lastSavedAt" to lastSavedAt
    )
}

data class ProjectMemoryRecord(
    val id: String = UUID.randomUUID().toString(),
    val projectId: String = "",
    val architectureSummary: String = "Clean Architecture with MVVM, Jetpack Compose, and Firebase Firestore.",
    val techStack: List<String> = listOf("Kotlin", "Jetpack Compose", "Firebase Firestore", "OkHttp"),
    val designDecisions: List<String> = listOf("Zero Trust Authentication", "AES-256-GCM Secret Vault", "Durable Background Workers"),
    val activeBranch: String = "work-branch",
    val lastCheckpointSha: String = "sha256_checkpoint_9988"
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "projectId" to projectId,
        "architectureSummary" to architectureSummary,
        "techStack" to techStack,
        "designDecisions" to designDecisions,
        "activeBranch" to activeBranch,
        "lastCheckpointSha" to lastCheckpointSha
    )
}
