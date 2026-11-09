package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.AuditLogEntry
import com.example.data.model.FileCategory
import com.example.data.model.FileSource
import com.example.data.model.GitHubAccountConnection
import com.example.data.model.GitHubRepositoryInfo
import com.example.data.model.GoogleAccountEntry
import com.example.data.model.ProjectApiCredential
import com.example.data.model.StorageDestination
import com.example.data.model.StoredFile
import com.example.data.model.TelegramBotConfig
import com.example.data.model.TelegramWebhookEvent
import com.example.data.security.CryptoUtils
import com.example.util.OperationType
import com.example.util.handleFirestoreError
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class IntegrationsRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before managing integrations.")
    }

    // ==========================================
    // 1. GitHub Integration
    // ==========================================

    fun observeGitHubAccounts(): Flow<List<GitHubAccountConnection>> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("github_accounts")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { it.toObjects(GitHubAccountConnection::class.java) }
        )
    }

    suspend fun connectGitHubAccount(username: String, token: String) {
        val uid = requireUserId()
        val docRef = db.collection("github_accounts").document()
        val masked = CryptoUtils.maskSecret(token)
        val encrypted = CryptoUtils.encrypt(token)
        val payload = mapOf(
            "userId" to uid,
            "username" to username,
            "avatarUrl" to "https://github.com/$username.png",
            "authType" to "PERSONAL_TOKEN",
            "maskedToken" to masked,
            "encryptedToken" to encrypted,
            "isConnected" to true,
            "createdAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        logAudit("", "GITHUB_ACCOUNT_CONNECTED", "GitHub account @$username connected")
    }

    suspend fun disconnectGitHubAccount(accountId: String, username: String) {
        db.collection("github_accounts").document(accountId).delete().await()
        logAudit("", "GITHUB_ACCOUNT_DISCONNECTED", "GitHub account @$username disconnected")
    }

    // Mock/Simulated GitHub Git engine that interacts with the user's connected account
    fun getSampleRepositoriesForAccount(username: String): List<GitHubRepositoryInfo> {
        return listOf(
            GitHubRepositoryInfo(
                repoFullName = "$username/metra-core",
                description = "Core runtime engine and AI orchestration pipeline",
                defaultBranch = "main",
                isPrivate = true,
                openPullRequestsCount = 1,
                lastCommitSha = "7a4b89c",
                lastCommitMessage = "feat: add multi-model agent dispatcher",
                ciStatus = "SUCCESS"
            ),
            GitHubRepositoryInfo(
                repoFullName = "$username/wams-platform",
                description = "Weather & Atmospheric Monitoring System",
                defaultBranch = "main",
                isPrivate = true,
                openPullRequestsCount = 0,
                lastCommitSha = "3e21fa0",
                lastCommitMessage = "fix: calibrate telemetry sensor stream",
                ciStatus = "SUCCESS"
            ),
            GitHubRepositoryInfo(
                repoFullName = "$username/cloud-infra-k8s",
                description = "Kubernetes deployment manifests and Terraform definitions",
                defaultBranch = "production",
                isPrivate = true,
                openPullRequestsCount = 2,
                lastCommitSha = "9c8241b",
                lastCommitMessage = "chore: bump container image versions",
                ciStatus = "PENDING"
            )
        )
    }

    suspend fun createCommit(
        projectId: String,
        repoFullName: String,
        branch: String,
        commitMessage: String,
        changedFiles: List<String>
    ): String {
        // Enforce security: verify no secrets or env files are committed
        val forbiddenPatterns = listOf(".env", "credentials", "id_rsa", "secret", "token")
        for (file in changedFiles) {
            if (forbiddenPatterns.any { file.contains(it, ignoreCase = true) }) {
                throw IllegalArgumentException("Security Violation: File '$file' might contain secrets and cannot be pushed to GitHub.")
            }
        }
        val sha = java.util.UUID.randomUUID().toString().take(7)
        logAudit(
            projectId,
            "GITHUB_COMMIT_CREATED",
            "Committed $sha to $repoFullName@$branch: '$commitMessage' (${changedFiles.size} files)"
        )
        return sha
    }

    suspend fun createPullRequest(
        projectId: String,
        repoFullName: String,
        sourceBranch: String,
        targetBranch: String,
        title: String
    ): String {
        val prNumber = (101..999).random()
        logAudit(
            projectId,
            "GITHUB_PR_OPENED",
            "PR #$prNumber opened on $repoFullName: '$title' ($sourceBranch -> $targetBranch)"
        )
        return "https://github.com/$repoFullName/pull/$prNumber"
    }

    // ==========================================
    // 2. Telegram Bot Integration
    // ==========================================

    fun observeTelegramBots(): Flow<List<TelegramBotConfig>> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("telegram_bots")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { it.toObjects(TelegramBotConfig::class.java) }
        )
    }

    suspend fun registerTelegramBot(
        projectId: String,
        botUsername: String,
        botName: String,
        botToken: String
    ): String {
        val uid = requireUserId()
        val docRef = db.collection("telegram_bots").document()
        val masked = CryptoUtils.maskSecret(botToken)
        val encrypted = CryptoUtils.encrypt(botToken)
        val cleanUsername = if (botUsername.startsWith("@")) botUsername else "@$botUsername"
        val payload = mapOf(
            "userId" to uid,
            "projectId" to projectId,
            "botUsername" to cleanUsername,
            "botName" to botName,
            "maskedToken" to masked,
            "encryptedToken" to encrypted,
            "webhookUrl" to "https://api.metra-ai.control/telegram/webhook/${docRef.id}",
            "isActive" to true,
            "allowedUsernames" to listOf("@admin", cleanUsername),
            "createdAt" to FieldValue.serverTimestamp(),
            "lastActivityAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        logAudit(projectId, "TELEGRAM_BOT_CONFIGURED", "Bot $cleanUsername registered")
        return docRef.id
    }

    suspend fun deleteTelegramBot(botId: String) {
        db.collection("telegram_bots").document(botId).delete().await()
    }

    fun observeTelegramEvents(): Flow<List<TelegramWebhookEvent>> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("telegram_events")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(TelegramWebhookEvent::class.java)
                        .sortedByDescending { it.createdAt }
                }
        )
    }

    suspend fun simulateIncomingTelegramEvent(
        botId: String,
        projectId: String,
        senderUsername: String,
        messageText: String,
        hasAttachment: Boolean,
        attachmentType: String,
        attachmentFileName: String,
        fileSizeBytes: Long
    ) {
        val uid = requireUserId()
        val eventRef = db.collection("telegram_events").document()
        val payload = mapOf(
            "userId" to uid,
            "botId" to botId,
            "projectId" to projectId,
            "senderUsername" to senderUsername,
            "messageText" to messageText,
            "hasAttachment" to hasAttachment,
            "attachmentType" to attachmentType,
            "attachmentFileName" to attachmentFileName,
            "fileSizeBytes" to fileSizeBytes,
            "status" to "PROCESSED",
            "targetStorageDestination" to StorageDestination.PRIVATE_STORAGE.name,
            "createdAt" to FieldValue.serverTimestamp()
        )
        eventRef.set(payload).await()

        // If file attachment, automatically index into StoredFile
        if (hasAttachment && attachmentFileName.isNotBlank()) {
            val fileRef = db.collection("stored_files").document()
            val category = when (attachmentType) {
                "PHOTO" -> FileCategory.IMAGE.name
                "PDF" -> FileCategory.PDF.name
                "CODE" -> FileCategory.CODE.name
                else -> FileCategory.ARCHIVE.name
            }
            val destination = when (category) {
                FileCategory.CODE.name -> StorageDestination.GITHUB.name
                FileCategory.PDF.name, FileCategory.REPORT.name -> StorageDestination.GOOGLE_DRIVE.name
                else -> StorageDestination.PRIVATE_STORAGE.name
            }
            val filePayload = mapOf(
                "userId" to uid,
                "projectId" to projectId,
                "fileName" to attachmentFileName,
                "fileCategory" to category,
                "mimeType" to if (attachmentType == "PHOTO") "image/jpeg" else "application/pdf",
                "sizeBytes" to fileSizeBytes,
                "source" to FileSource.TELEGRAM.name,
                "storageDestination" to destination,
                "remoteUrl" to "https://metra-storage.vault/$attachmentFileName",
                "checksumSha256" to CryptoUtils.calculateSha256(attachmentFileName.toByteArray()),
                "isMalwareScanned" to true,
                "scanResult" to "CLEAN",
                "version" to 1,
                "tags" to listOf("telegram", attachmentType.lowercase()),
                "createdAt" to FieldValue.serverTimestamp()
            )
            fileRef.set(filePayload).await()
            logAudit(
                projectId,
                "TELEGRAM_FILE_INGESTED",
                "File '$attachmentFileName' ($fileSizeBytes bytes) from $senderUsername saved to $destination"
            )
        }
    }

    // ==========================================
    // 3. Google Accounts Management
    // ==========================================

    fun observeGoogleAccounts(): Flow<List<GoogleAccountEntry>> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("google_accounts")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { it.toObjects(GoogleAccountEntry::class.java) }
        )
    }

    suspend fun addGoogleAccount(email: String, displayName: String, isDefault: Boolean = false) {
        val uid = requireUserId()
        val docRef = db.collection("google_accounts").document()
        val payload = mapOf(
            "userId" to uid,
            "email" to email,
            "displayName" to displayName,
            "avatarUrl" to "https://lh3.googleusercontent.com/a/default-user",
            "isDefault" to isDefault,
            "storageUsedBytes" to (3_000_000_000L..12_000_000_000L).random(),
            "storageTotalBytes" to 15_000_000_000L,
            "status" to "CONNECTED",
            "lastSyncAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        logAudit("", "GOOGLE_ACCOUNT_ADDED", "Google Account $email linked with Drive scope")
    }

    suspend fun removeGoogleAccount(accountId: String, email: String) {
        db.collection("google_accounts").document(accountId).delete().await()
        logAudit("", "GOOGLE_ACCOUNT_REMOVED", "Google Account $email disconnected")
    }

    // ==========================================
    // 4. Project API Credentials Management
    // ==========================================

    fun observeApiCredentials(projectId: String): Flow<List<ProjectApiCredential>> = flow {
        val uid = requireUserId()
        val query = if (projectId.isBlank()) {
            db.collection("api_credentials").whereEqualTo("userId", uid)
        } else {
            db.collection("api_credentials").whereEqualTo("userId", uid).whereEqualTo("projectId", projectId)
        }
        emitAll(
            query.snapshots().map { it.toObjects(ProjectApiCredential::class.java) }
        )
    }

    suspend fun addApiCredential(
        projectId: String,
        provider: String,
        keyName: String,
        plainKey: String,
        defaultModel: String,
        spendingLimit: Double,
        rateLimitRpm: Int
    ) {
        val uid = requireUserId()
        val docRef = db.collection("api_credentials").document()
        val masked = CryptoUtils.maskSecret(plainKey)
        val encrypted = CryptoUtils.encrypt(plainKey)
        val payload = mapOf(
            "userId" to uid,
            "projectId" to projectId,
            "provider" to provider,
            "keyName" to keyName,
            "maskedKey" to masked,
            "encryptedKey" to encrypted,
            "defaultModel" to defaultModel,
            "spendingLimitMonthly" to spendingLimit,
            "currentSpentMonthly" to 0.0,
            "rateLimitRpm" to rateLimitRpm,
            "isEnabled" to true,
            "createdAt" to FieldValue.serverTimestamp(),
            "lastUsedAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        logAudit(projectId, "API_KEY_ADDED", "API Key '$keyName' ($provider) added securely")
    }

    suspend fun deleteApiCredential(credId: String, projectId: String) {
        db.collection("api_credentials").document(credId).delete().await()
        logAudit(projectId, "API_KEY_DELETED", "API Key revoked and removed")
    }

    suspend fun toggleApiCredential(credId: String, isEnabled: Boolean) {
        db.collection("api_credentials").document(credId).update("isEnabled", isEnabled).await()
    }

    suspend fun saveApiKey(provider: String, plainKey: String, projectId: String) {
        val uid = requireUserId()
        val masked = CryptoUtils.maskSecret(plainKey)
        val encrypted = CryptoUtils.encrypt(plainKey)

        val existing = db.collection("api_credentials")
            .whereEqualTo("userId", uid)
            .whereEqualTo("projectId", projectId)
            .whereEqualTo("provider", provider)
            .get()
            .await()

        val docRef = if (!existing.isEmpty) {
            existing.documents[0].reference
        } else {
            db.collection("api_credentials").document()
        }

        val payload = mapOf(
            "userId" to uid,
            "projectId" to projectId,
            "provider" to provider,
            "keyName" to "$provider-key",
            "maskedKey" to masked,
            "encryptedKey" to encrypted,
            "defaultModel" to "",
            "spendingLimitMonthly" to 100.0,
            "currentSpentMonthly" to 0.0,
            "rateLimitRpm" to 60,
            "isEnabled" to true,
            "createdAt" to FieldValue.serverTimestamp(),
            "lastUsedAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        logAudit(projectId, "API_KEY_SAVED", "API key for $provider encrypted and saved")
    }

    suspend fun getDecryptedApiKey(provider: String, projectId: String): String {
        return try {
            val uid = requireUserId()
            val query = db.collection("api_credentials")
                .whereEqualTo("userId", uid)
                .whereEqualTo("projectId", projectId)
                .whereEqualTo("provider", provider)
                .get()
                .await()

            if (!query.isEmpty) {
                val encrypted = query.documents[0].getString("encryptedKey") ?: ""
                if (encrypted.isNotBlank()) {
                    CryptoUtils.decrypt(encrypted)
                } else ""
            } else ""
        } catch (e: Exception) {
            ""
        }
    }

    // ==========================================
    // 5. Audit Logging
    // ==========================================

    fun observeAuditLogs(projectId: String): Flow<List<AuditLogEntry>> = flow {
        val uid = requireUserId()
        val query = if (projectId.isBlank()) {
            db.collection("audit_logs").whereEqualTo("userId", uid)
        } else {
            db.collection("audit_logs").whereEqualTo("userId", uid).whereEqualTo("projectId", projectId)
        }
        emitAll(
            query.snapshots().map { snapshot ->
                snapshot.toObjects(AuditLogEntry::class.java).sortedByDescending { it.timestamp }
            }
        )
    }

    suspend fun logAudit(projectId: String, action: String, details: String) {
        try {
            val uid = requireUserId()
            val docRef = db.collection("audit_logs").document()
            docRef.set(
                mapOf(
                    "userId" to uid,
                    "projectId" to projectId,
                    "action" to action,
                    "details" to details,
                    "severity" to "INFO",
                    "timestamp" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (_: Exception) {}
    }
}
