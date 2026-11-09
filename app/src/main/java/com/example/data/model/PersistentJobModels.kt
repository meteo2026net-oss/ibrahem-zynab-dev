package com.example.data.model

import java.util.UUID

enum class JobStatus(val id: String, val titleAr: String) {
    QUEUED("queued", "في قائمة الانتظار"),
    RUNNING("running", "قيد التنفيذ الخلفي"),
    WAITING_FOR_APPROVAL("waiting_approval", "بانتظار الموافقة البشرية"),
    WAITING_FOR_RETRY("waiting_retry", "بانتظار إعادة المحاولة"),
    PAUSED("paused", "متوقفة مؤقتاً"),
    COMPLETED("completed", "مكتملة بنجاح"),
    FAILED("failed", "فشلت"),
    CANCELLED("cancelled", "ملغاة")
}

enum class JobStepType(val id: String, val titleAr: String, val isSensitive: Boolean) {
    ANALYSIS("analysis", "فحص المتطلبات والكود", false),
    CODE_FIX("code_fix", "إصلاح الأخطاء وتحديث الشيفرة", false),
    SECURITY_REVIEW("security_review", "التدقيق الأمني واكتشاف الثغرات", false),
    TESTING("testing", "تشغيل الاختبارات والتحقق", false),
    APPROVAL_GATE("approval_gate", "بوابة الموافقة البشرية", true),
    GIT_PUSH("git_push", "رفع التغييرات إلى GitHub", true),
    DEPLOY("deploy", "النشر والتوزيع", true),
    NOTIFY("notify", "إرسال التقارير والإشعارات", false)
}

data class JobStep(
    val stepId: String = UUID.randomUUID().toString(),
    val stepIndex: Int,
    val title: String,
    val description: String,
    val stepType: JobStepType,
    val assignedRole: AgentRole,
    var status: JobStatus = JobStatus.QUEUED,
    val isApprovalRequired: Boolean = stepType.isSensitive,
    var checkpointOutput: String? = null,
    val idempotencyKey: String? = null,
    var tokensUsed: Int = 0,
    var costUsd: Double = 0.0,
    var startedAt: Long? = null,
    var completedAt: Long? = null,
    var errorMessage: String? = null
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "stepId" to stepId,
        "stepIndex" to stepIndex,
        "title" to title,
        "description" to description,
        "stepType" to stepType.id,
        "assignedRole" to assignedRole.id,
        "status" to status.id,
        "isApprovalRequired" to isApprovalRequired,
        "checkpointOutput" to checkpointOutput,
        "idempotencyKey" to idempotencyKey,
        "tokensUsed" to tokensUsed,
        "costUsd" to costUsd,
        "startedAt" to startedAt,
        "completedAt" to completedAt,
        "errorMessage" to errorMessage
    )

    companion object {
        fun fromMap(map: Map<String, Any?>): JobStep {
            val typeStr = map["stepType"] as? String ?: "analysis"
            val stepType = JobStepType.values().find { it.id == typeStr } ?: JobStepType.ANALYSIS
            val roleStr = map["assignedRole"] as? String ?: "developer"
            val role = AgentRole.values().find { it.id == roleStr } ?: AgentRole.CODE_DEVELOPER
            val statusStr = map["status"] as? String ?: "queued"
            val status = JobStatus.values().find { it.id == statusStr } ?: JobStatus.QUEUED

            return JobStep(
                stepId = map["stepId"] as? String ?: UUID.randomUUID().toString(),
                stepIndex = (map["stepIndex"] as? Number)?.toInt() ?: 0,
                title = map["title"] as? String ?: "",
                description = map["description"] as? String ?: "",
                stepType = stepType,
                assignedRole = role,
                status = status,
                isApprovalRequired = map["isApprovalRequired"] as? Boolean ?: stepType.isSensitive,
                checkpointOutput = map["checkpointOutput"] as? String,
                idempotencyKey = map["idempotencyKey"] as? String,
                tokensUsed = (map["tokensUsed"] as? Number)?.toInt() ?: 0,
                costUsd = (map["costUsd"] as? Number)?.toDouble() ?: 0.0,
                startedAt = (map["startedAt"] as? Number)?.toLong(),
                completedAt = (map["completedAt"] as? Number)?.toLong(),
                errorMessage = map["errorMessage"] as? String
            )
        }
    }
}

data class PersistentJob(
    val jobId: String = UUID.randomUUID().toString(),
    val userId: String,
    val projectId: String,
    val projectName: String,
    val title: String,
    val userPrompt: String,
    var status: JobStatus = JobStatus.QUEUED,
    val steps: MutableList<JobStep> = mutableListOf(),
    var currentStepIndex: Int = 0,
    var progressPercent: Int = 0,
    var workerId: String? = null,
    var leaseToken: String? = null,
    var leaseExpiresAt: Long? = null,
    var heartbeatAt: Long? = null,
    var retryCount: Int = 0,
    val maxRetries: Int = 3,
    var waitingApprovalId: String? = null,
    var totalTokensUsed: Int = 0,
    var estimatedCostUsd: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis(),
    var updatedAt: Long = System.currentTimeMillis(),
    var completedAt: Long? = null,
    val executionLogs: MutableList<String> = mutableListOf()
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "projectId" to projectId,
        "projectName" to projectName,
        "title" to title,
        "userPrompt" to userPrompt,
        "status" to status.id,
        "currentStepIndex" to currentStepIndex,
        "totalSteps" to steps.size,
        "progressPercent" to progressPercent,
        "workerId" to workerId,
        "leaseToken" to leaseToken,
        "leaseExpiresAt" to leaseExpiresAt,
        "heartbeatAt" to heartbeatAt,
        "retryCount" to retryCount,
        "maxRetries" to maxRetries,
        "waitingApprovalId" to waitingApprovalId,
        "totalTokensUsed" to totalTokensUsed,
        "estimatedCostUsd" to estimatedCostUsd,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt,
        "completedAt" to completedAt,
        "stepsData" to steps.map { it.toMap() },
        "logs" to executionLogs.takeLast(50)
    )

    fun hasValidLease(): Boolean {
        val now = System.currentTimeMillis()
        return leaseToken != null && (leaseExpiresAt ?: 0L) > now
    }

    fun isLeaseExpired(): Boolean {
        val now = System.currentTimeMillis()
        return leaseToken != null && (leaseExpiresAt ?: 0L) <= now
    }
}

data class IdempotencyRecord(
    val idempotencyKey: String,
    val userId: String,
    val actionType: String,
    val status: String,
    val resultPayload: String,
    val executedAt: Long = System.currentTimeMillis()
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "idempotencyKey" to idempotencyKey,
        "actionType" to actionType,
        "status" to status,
        "resultPayload" to resultPayload,
        "executedAt" to executedAt
    )
}
