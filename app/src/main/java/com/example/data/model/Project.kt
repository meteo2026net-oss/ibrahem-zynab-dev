package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

enum class ProjectType(val displayName: String, val iconName: String) {
    MOBILE("Mobile App (Android/iOS)", "phone_android"),
    WEB("Web Application", "language"),
    AI_SYSTEM("AI System & Agents", "psychology"),
    BACKEND_API("Backend & Microservices", "dns"),
    CLOUD_INFRA("Cloud Infrastructure", "cloud"),
    DESKTOP("Desktop Application", "desktop_windows"),
    EMBEDDED("Embedded & IoT", "memory")
}

enum class ProjectStatus(val titleAr: String, val titleEn: String) {
    ACTIVE("نشط", "Active"),
    PLANNING("قيد التخطيط", "Planning"),
    IN_PROGRESS("قيد التطوير", "In Progress"),
    TESTING("قيد الاختبار", "Testing"),
    COMPLETED("مكتمل", "Completed"),
    ARCHIVED("مؤرشف", "Archived"),
    ON_HOLD("معلق", "On Hold")
}

@IgnoreExtraProperties
data class StorageRoutingConfig(
    val codeDestination: String = "GITHUB",          // GITHUB, PRIVATE_STORAGE
    val docsDestination: String = "GOOGLE_DRIVE",    // GOOGLE_DRIVE, PRIVATE_STORAGE
    val mediaDestination: String = "PRIVATE_STORAGE", // PRIVATE_STORAGE, GOOGLE_DRIVE
    val backupsDestination: String = "GOOGLE_DRIVE"  // GOOGLE_DRIVE, PRIVATE_STORAGE
)

@IgnoreExtraProperties
data class Project(
    @DocumentId val id: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val projectType: String = ProjectType.AI_SYSTEM.name,
    val language: String = "Kotlin / Python",
    val framework: String = "Compose / FastApi",
    val status: String = ProjectStatus.ACTIVE.name,
    val progress: Int = 0,
    val defaultAiProvider: String = "Gemini",
    val defaultAiModel: String = "gemini-1.5-pro",
    val githubRepo: String = "",
    val githubBranch: String = "main",
    val googleDriveFolderId: String = "",
    val googleDriveAccountEmail: String = "",
    val telegramBotId: String = "",
    val storageRouting: StorageRoutingConfig = StorageRoutingConfig(),
    val openTasksCount: Int = 0,
    val completedTasksCount: Int = 0,
    val notes: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
