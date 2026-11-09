package com.example.data.model

import java.util.UUID

enum class VisionAnalysisTask(val titleAr: String, val titleEn: String) {
    GENERAL_DESCRIPTION("وصف محتوى الصورة", "Describe Content"),
    OCR_TEXT_EXTRACTION("استخراج النصوص OCR", "Extract Text (OCR)"),
    CHART_DIAGRAM_ANALYSIS("تحليل المخططات والرسوم البيانانية", "Analyze Charts & Diagrams"),
    ERROR_LOG_PARSING("قراءة صور الأخطاء البرمجية", "Parse Code Errors"),
    SCREENSHOT_UI_ANALYSIS("تحليل صور واجهات التطبيق", "Analyze UI Screenshots"),
    COMPARE_IMAGES("مقارنة الصور", "Compare Images"),
    CODE_IMPROVEMENT_SUGGESTION("اقتراح تعديلات برمجية بناءً على الصورة", "Suggest Code Changes")
}

data class ProjectAttachment(
    val id: String = UUID.randomUUID().toString(),
    val fileName: String,
    val fileUri: String = "",
    val mimeType: String = "image/jpeg",
    val sizeBytes: Long = 0L,
    var caption: String = "",
    val category: FileCategory = FileCategory.IMAGE,
    val base64Data: String? = null,
    val isFromCamera: Boolean = false,
    val destination: StorageDestination = StorageDestination.PRIVATE_STORAGE
) {
    fun getFormattedSize(): String {
        return when {
            sizeBytes < 1024 -> "$sizeBytes B"
            sizeBytes < 1024 * 1024 -> "${sizeBytes / 1024} KB"
            else -> "%.1f MB".format(sizeBytes / (1024.0 * 1024.0))
        }
    }

    val isImage: Boolean
        get() = mimeType.startsWith("image/") || category == FileCategory.IMAGE
}

data class VisionAnalysisResult(
    val id: String = UUID.randomUUID().toString(),
    val attachmentId: String,
    val fileName: String,
    val analysisTask: VisionAnalysisTask,
    val extractedText: String = "",
    val aiAnalysisSummary: String,
    val suggestedCodeFixes: String = "",
    val detectedLayoutElements: List<String> = emptyList(),
    val modelUsed: String = "gemini-2.5-flash",
    val timestamp: Long = System.currentTimeMillis()
)
