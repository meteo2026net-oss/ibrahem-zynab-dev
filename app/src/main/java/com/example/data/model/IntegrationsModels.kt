package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class GoogleAccountEntry(
    @DocumentId val id: String = "",
    val userId: String = "",
    val email: String = "",
    val displayName: String = "",
    val avatarUrl: String = "",
    val isDefault: Boolean = false,
    val storageUsedBytes: Long = 4_200_000_000L,
    val storageTotalBytes: Long = 15_000_000_000L,
    val status: String = "CONNECTED", // CONNECTED, EXPIRED, DISCONNECTED
    @ServerTimestamp val lastSyncAt: Timestamp? = null
)

@IgnoreExtraProperties
data class ProjectApiCredential(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val provider: String = "OpenAI", // OpenAI, Gemini, Anthropic, Mistral, Groq
    val keyName: String = "",
    val maskedKey: String = "",
    val encryptedKey: String = "",
    val defaultModel: String = "gpt-4o",
    val spendingLimitMonthly: Double = 100.0,
    val currentSpentMonthly: Double = 14.50,
    val rateLimitRpm: Int = 120,
    val isEnabled: Boolean = true,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val lastUsedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class GitHubAccountConnection(
    @DocumentId val id: String = "",
    val userId: String = "",
    val username: String = "",
    val avatarUrl: String = "",
    val authType: String = "PERSONAL_TOKEN", // PERSONAL_TOKEN, OAUTH_APP
    val maskedToken: String = "",
    val encryptedToken: String = "",
    val isConnected: Boolean = true,
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class GitHubRepositoryInfo(
    val repoFullName: String = "", // owner/repo
    val description: String = "",
    val defaultBranch: String = "main",
    val isPrivate: Boolean = true,
    val openPullRequestsCount: Int = 0,
    val lastCommitSha: String = "",
    val lastCommitMessage: String = "",
    val ciStatus: String = "SUCCESS" // SUCCESS, PENDING, FAILURE, NONE
)

@IgnoreExtraProperties
data class TelegramBotConfig(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "", // empty means Global Bot
    val botUsername: String = "",
    val botName: String = "",
    val maskedToken: String = "",
    val encryptedToken: String = "",
    val webhookUrl: String = "",
    val isActive: Boolean = true,
    val allowedUsernames: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val lastActivityAt: Timestamp? = null
)

@IgnoreExtraProperties
data class TelegramWebhookEvent(
    @DocumentId val id: String = "",
    val userId: String = "",
    val botId: String = "",
    val projectId: String = "",
    val senderUsername: String = "",
    val messageText: String = "",
    val hasAttachment: Boolean = false,
    val attachmentType: String = "", // PHOTO, DOCUMENT, PDF, CODE
    val attachmentFileName: String = "",
    val fileSizeBytes: Long = 0L,
    val status: String = "PROCESSED", // PROCESSED, REJECTED, ROUTED
    val targetStorageDestination: String = StorageDestination.PRIVATE_STORAGE.name,
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class AiAgent(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val name: String = "",
    val role: String = "",
    val description: String = "",
    val systemInstructions: String = "",
    val model: String = "gemini-1.5-pro",
    val status: String = "IDLE", // IDLE, WORKING, BUSY
    val permissions: List<String> = listOf("READ_FILES", "WRITE_CODE", "RUN_TESTS"),
    val lastActiveTask: String = ""
)

@IgnoreExtraProperties
data class AuditLogEntry(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val action: String = "",
    val details: String = "",
    val severity: String = "INFO", // INFO, WARNING, CRITICAL
    @ServerTimestamp val timestamp: Timestamp? = null
)
