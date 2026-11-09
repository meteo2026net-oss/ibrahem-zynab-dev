package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

enum class TaskStatus(val titleAr: String, val titleEn: String) {
    TODO("قيد الانتظار", "To Do"),
    IN_PROGRESS("قيد التنفيذ", "In Progress"),
    REVIEW("مراجعة الكود", "Review"),
    DONE("مكتملة", "Done")
}

enum class TaskPriority(val titleAr: String, val titleEn: String) {
    LOW("منخفضة", "Low"),
    MEDIUM("متوسطة", "Medium"),
    HIGH("عالية", "High"),
    CRITICAL("حرجة", "Critical")
}

@IgnoreExtraProperties
data class ProjectTask(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val title: String = "",
    val description: String = "",
    val assignedAgent: String = "Software Engineer",
    val status: String = TaskStatus.TODO.name,
    val priority: String = TaskPriority.HIGH.name,
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
