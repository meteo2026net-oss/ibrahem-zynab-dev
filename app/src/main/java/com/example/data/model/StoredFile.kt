package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

enum class FileCategory(val titleAr: String, val titleEn: String) {
    CODE("كود مصدري", "Source Code"),
    IMAGE("صور", "Images"),
    PDF("مستندات PDF", "PDF Documents"),
    BACKUP("نسخ احتياطية", "Backups"),
    REPORT("تقارير", "Reports"),
    CONFIG("ملفات إعدادات", "Configurations"),
    ARCHIVE("أرشيف ومرفقات", "Archives & Attachments")
}

enum class StorageDestination(val titleAr: String, val titleEn: String) {
    GITHUB("مستودع GitHub", "GitHub Repository"),
    GOOGLE_DRIVE("Google Drive", "Google Drive"),
    PRIVATE_STORAGE("تخزين خاص مشفر", "Private Object Storage")
}

enum class FileSource(val titleAr: String, val titleEn: String) {
    TELEGRAM("بوت تيليجرام", "Telegram Bot"),
    GITHUB("مستودع GitHub", "GitHub"),
    DRIVE("Google Drive", "Google Drive"),
    DIRECT_UPLOAD("رفع مباشر من التطبيق", "Direct App Upload"),
    AI_GENERATOR("توليد الذكاء الاصطناعي", "AI Generated")
}

@IgnoreExtraProperties
data class StoredFile(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val fileName: String = "",
    val fileCategory: String = FileCategory.CODE.name,
    val mimeType: String = "application/octet-stream",
    val sizeBytes: Long = 0L,
    val source: String = FileSource.DIRECT_UPLOAD.name,
    val storageDestination: String = StorageDestination.PRIVATE_STORAGE.name,
    val remoteUrl: String = "",
    val remotePath: String = "",
    val localCachedPath: String = "",
    val checksumSha256: String = "",
    val isMalwareScanned: Boolean = true,
    val scanResult: String = "CLEAN", // CLEAN, SUSPICIOUS, BLOCKED
    val version: Int = 1,
    val tags: List<String> = emptyList(),
    @ServerTimestamp val createdAt: Timestamp? = null
) {
    fun getFormattedSize(): String {
        return when {
            sizeBytes < 1024 -> "$sizeBytes B"
            sizeBytes < 1024 * 1024 -> "${sizeBytes / 1024} KB"
            else -> "%.1f MB".format(sizeBytes / (1024.0 * 1024.0))
        }
    }
}
