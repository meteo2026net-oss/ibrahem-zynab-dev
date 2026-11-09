package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

enum class ApprovalOperation(val titleAr: String, val titleEn: String) {
    PUSH_COMMIT("الموافقة على رفع التغييرات", "Push Commit"),
    CREATE_PR("الموافقة على إنشاء Pull Request", "Create Pull Request"),
    MERGE_PR("الموافقة على دمج الفرع", "Merge Pull Request"),
    DELETE_FILE("الموافقة على حذف ملفات", "Delete Files"),
    DEPLOY("الموافقة على النشر", "Deploy Release"),
    CHANGE_SETTINGS("الموافقة على تغيير إعدادات المشروع", "Change Project Settings")
}

enum class ApprovalStatus(val titleAr: String, val titleEn: String) {
    PENDING("بانتظار الموافقة", "Pending"),
    APPROVED("تمت الموافقة", "Approved"),
    REJECTED("مرفوض", "Rejected"),
    EXPIRED("منتهية الصلاحية", "Expired")
}

@IgnoreExtraProperties
data class InteractiveApprovalRequest(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val projectName: String = "",
    val operationType: String = ApprovalOperation.PUSH_COMMIT.name,
    val description: String = "",
    val approvalToken: String = "",
    val status: String = ApprovalStatus.PENDING.name,
    val expiresAt: Long = 0L,
    val requestedBy: String = "Telegram / Agent",
    val approvedBy: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val resolvedAt: Timestamp? = null
) {
    fun isExpired(): Boolean = System.currentTimeMillis() > expiresAt && status == ApprovalStatus.PENDING.name
}

enum class SyncStatus(val titleAr: String, val titleEn: String) {
    SYNCING("جارِ المزامنة", "Syncing"),
    COMPLETED("مكتملة بنجاح", "Completed"),
    FAILED("فشلت المزامنة", "Failed"),
    CONFLICT_DETECTED("تم كشف تعارض", "Conflict Detected")
}

@IgnoreExtraProperties
data class SyncRecord(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val syncType: String = "MANUAL", // MANUAL, SCHEDULED, TRIGGERED
    val destinations: List<String> = listOf("GITHUB", "GOOGLE_DRIVE", "PRIVATE_STORAGE", "TELEGRAM"),
    val status: String = SyncStatus.COMPLETED.name,
    val syncedFilesCount: Int = 0,
    val failedFilesCount: Int = 0,
    val failedFiles: List<String> = emptyList(),
    val conflictFiles: List<String> = emptyList(),
    val details: String = "",
    @ServerTimestamp val startedAt: Timestamp? = null,
    @ServerTimestamp val completedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class ProjectBackupSnapshot(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val projectName: String = "",
    val snapshotVersion: String = "v1.0.0",
    val encryptedData: String = "",
    val sha256Checksum: String = "",
    val backupSizeBytes: Long = 0L,
    val status: String = "VERIFIED", // CREATED, VERIFIED, FAILED, RESTORED
    val retentionDays: Int = 30,
    val destination: String = "GOOGLE_DRIVE",
    @ServerTimestamp val createdAt: Timestamp? = null
) {
    fun getFormattedSize(): String {
        return when {
            backupSizeBytes < 1024 -> "$backupSizeBytes B"
            backupSizeBytes < 1024 * 1024 -> "${backupSizeBytes / 1024} KB"
            else -> "%.1f MB".format(backupSizeBytes / (1024.0 * 1024.0))
        }
    }
}

enum class NotificationType(val titleAr: String, val titleEn: String) {
    TASK_COMPLETED("اكتمال مهمة", "Task Completed"),
    TEST_FAILURE("فشل اختبار", "Test Failure"),
    CI_FAILURE("فشل GitHub CI", "CI Failure"),
    PR_CREATED("إنشاء Pull Request", "PR Created"),
    APPROVAL_NEEDED("مطلوب موافقة", "Approval Needed"),
    STORAGE_ALERT("تنبيه مساحة التخزين", "Storage Alert"),
    SYNC_FAILURE("فشل المزامنة", "Sync Failure"),
    BACKUP_FAILURE("فشل النسخ الاحتياطي", "Backup Failure"),
    SECURITY_ALERT("تنبيه أمني", "Security Alert"),
    INFO("إشعار عام", "General Info")
}

@IgnoreExtraProperties
data class AppNotification(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val title: String = "",
    val message: String = "",
    val type: String = NotificationType.INFO.name,
    val severity: String = "INFO", // INFO, WARNING, CRITICAL
    val isRead: Boolean = false,
    val sentToTelegram: Boolean = true,
    val actionUrl: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

data class FileIntelligenceResult(
    val fileName: String,
    val fileSizeBytes: Long,
    val mimeType: String,
    val detectedCategory: FileCategory,
    val extractedTextSnippet: String,
    val aiSuggestedProjectName: String,
    val aiSuggestedDestination: StorageDestination,
    val aiGeneratedSummary: String,
    val isDuplicate: Boolean,
    val duplicateChecksum: String,
    val suggestedTags: List<String>,
    val linkedTaskId: String = ""
)
