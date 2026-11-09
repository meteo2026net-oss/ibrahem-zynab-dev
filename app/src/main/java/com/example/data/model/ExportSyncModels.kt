package com.example.data.model

import java.util.UUID

enum class ExportDestinationType(val titleAr: String, val titleEn: String) {
    DEVICE_LOCAL("ذاكرة الجهاز (ZIP/Files)", "Local Device Memory"),
    GITHUB("مستودع GitHub", "GitHub Repository"),
    GOOGLE_DRIVE("Google Drive", "Google Drive"),
    TELEGRAM_BOT("بوت تيليجرام", "Telegram Bot")
}

enum class ExportScope(val titleAr: String, val titleEn: String) {
    FULL_PROJECT("المشروع كاملاً", "Full Project"),
    FOLDER_SELECTIVE("مجلد محدد", "Specific Folder"),
    MULTI_FILE("ملفات متعددة", "Multiple Files"),
    SINGLE_FILE("ملف منفرد", "Single File"),
    CUSTOM_TEMPLATE("قالب تصدير مخصص", "Custom Template")
}

enum class ExportTemplate(val titleAr: String, val descriptionAr: String) {
    FULL_PROJECT_ZIP("نسخة كاملة من المشروع", "يتضمن كامل الكود، التوثيق، الإعدادات والأصول"),
    SOURCE_CODE_ONLY("الكود المصدري فقط", "استبعاد ملفات التوثيق والأصول الثقيلة والـ build"),
    DOCS_REPORTS_ONLY("الوثائق والتقارير فقط", "ملفات Markdown وPDF والتقارير التنفيذية"),
    CLEAN_DB_SCHEMA("مخططات قواعد البيانات المنظفة", "هيكل الداتابيز ومخططات SQL دون الأسرار"),
    VISUAL_ASSETS("الصور والمرفقات البصرية", "جميع الصور والملفات المرفقة بالمشروع"),
    ENCRYPTED_BACKUP("نسخة احتياطية كاملة مشفرة", "نسخة آمنة مشفرة بكلمة سر وبصمة رقمية")
}

enum class SyncMode(val titleAr: String, val descriptionAr: String) {
    UPLOAD_ONLY("رفع فقط (Upload Only)", "إرسال التغييرات من المنصة إلى الوجهة دون سحب"),
    DOWNLOAD_ONLY("تنزيل فقط (Download Only)", "سحب التغييرات من الوجهة إلى المنصة دون رفع"),
    BIDIRECTIONAL("مزامنة ثنائية الاتجاه", "مزامنة كاملة مع اكتشاف التعارضات وحفظ النسخ"),
    NON_DESTRUCTIVE_BACKUP("نسخ احتياطي دون تعديل", "إنشاء نسخة احتياطية آمنة دون مساس الملف الأصلي"),
    SELECTIVE_TYPES("مزامنة انتقائية حسب النوع", "مزامنة أنواع محددة فقط (مثل .kt أو .pdf)")
}

enum class SyncTrigger(val titleAr: String) {
    MANUAL("مزامنة يدوية بطلب المستخدم"),
    SCHEDULED_CRON("جدولة زمنية دورية"),
    ON_TASK_COMPLETE("عند اكتمال مهمة برمجية"),
    ON_TEST_PASS("بعد نجاح الاختبارات الآلية"),
    ON_FILE_EDIT("عند تعديل ملف مصدر"),
    ON_VERSION_RELEASE("عند إنشاء إصدار جديد")
}

data class SyncTaskJob(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val projectId: String = "",
    val projectName: String = "",
    val destination: ExportDestinationType = ExportDestinationType.DEVICE_LOCAL,
    val scope: ExportScope = ExportScope.FULL_PROJECT,
    val template: ExportTemplate = ExportTemplate.FULL_PROJECT_ZIP,
    val syncMode: SyncMode = SyncMode.BIDIRECTIONAL,
    val trigger: SyncTrigger = SyncTrigger.MANUAL,
    val status: String = "QUEUED", // QUEUED, COMPRESSING, UPLOADING, COMPLETED, CONFLICT_DETECTED, PAUSED, FAILED
    val progressPercent: Int = 0,
    val transferredBytes: Long = 0L,
    val totalBytes: Long = 0L,
    val successfulFilesCount: Int = 0,
    val failedFilesCount: Int = 0,
    val sha256Fingerprint: String = "",
    val targetPathOrRepo: String = "",
    val targetBranch: String = "main",
    val hasConflict: Boolean = false,
    val conflictDetails: String = "",
    val executionLogs: List<String> = listOf("تم تسجيل المهمة في طابور العمال الدائم"),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "projectId" to projectId,
        "projectName" to projectName,
        "destination" to destination.name,
        "scope" to scope.name,
        "template" to template.name,
        "syncMode" to syncMode.name,
        "trigger" to trigger.name,
        "status" to status,
        "progressPercent" to progressPercent,
        "transferredBytes" to transferredBytes,
        "totalBytes" to totalBytes,
        "successfulFilesCount" to successfulFilesCount,
        "failedFilesCount" to failedFilesCount,
        "sha256Fingerprint" to sha256Fingerprint,
        "targetPathOrRepo" to targetPathOrRepo,
        "targetBranch" to targetBranch,
        "hasConflict" to hasConflict,
        "conflictDetails" to conflictDetails,
        "executionLogs" to executionLogs,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt
    )
}
