package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.AgentRole
import com.example.data.model.JobStatus
import com.example.data.model.JobStep
import com.example.data.model.JobStepType
import com.example.data.model.PersistentJob
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

internal fun parsePersistentJobStatus(raw: String?): JobStatus {
    return when (raw?.trim()?.uppercase()) {
        "SUCCEEDED", "SUCCESS", "COMPLETED" -> JobStatus.COMPLETED
        "WAITING_FOR_APPROVAL", "WAITING_APPROVAL" -> JobStatus.WAITING_FOR_APPROVAL
        "WAITING_FOR_RETRY", "WAITING_RETRY" -> JobStatus.WAITING_FOR_RETRY
        else -> JobStatus.values().firstOrNull { status ->
            status.id.equals(raw, ignoreCase = true) || status.name.equals(raw, ignoreCase = true)
        } ?: JobStatus.QUEUED
    }
}

internal fun parseFirestoreMillis(value: Any?): Long? = when (value) {
    is Number -> value.toLong()
    is Timestamp -> value.toDate().time
    is Date -> value.time
    else -> null
}

class PersistentJobRepository(private val context: Context) {
    private val firestore by lazy {
        Firebase.firestore(context.getString(R.string.firestore_database_id))
    }
    private val functions by lazy { FirebaseFunctions.getInstance() }

    /**
     * Creates a durable server-side queue record. This does not execute the task:
     * the trusted worker/dispatcher must be deployed before queued work can run.
     */
    suspend fun createAndScheduleJob(
        projectId: String,
        projectName: String,
        title: String,
        userPrompt: String,
        customSteps: List<JobStep>? = null
    ): PersistentJob = withContext(Dispatchers.IO) {
        val user = Firebase.auth.currentUser
            ?: throw IllegalStateException("Sign in before creating a background job.")
        require(projectId.isNotBlank()) { "Project ID is required." }
        require(title.isNotBlank()) { "Job title is required." }
        require(userPrompt.isNotBlank()) { "Job prompt is required." }

        val response = functions.getHttpsCallable("createPersistentJob").call(
            mapOf(
                "projectId" to projectId,
                "projectName" to projectName,
                "jobType" to "APP_DESIGN",
                "title" to title,
                "prompt" to userPrompt,
                "idempotencyKey" to UUID.randomUUID().toString()
            )
        ).await()
        val payload = response.data as? Map<*, *>
            ?: throw IllegalStateException("Trusted job service returned an invalid response.")
        val jobId = payload["jobId"] as? String
            ?: throw IllegalStateException("Trusted job service did not return a job ID.")
        val reused = payload["reused"] as? Boolean ?: false
        val steps = customSteps ?: generateDefaultPipelineSteps(jobId, userPrompt)
        val now = System.currentTimeMillis()

        PersistentJob(
            jobId = jobId,
            userId = user.uid,
            projectId = projectId,
            projectName = projectName,
            title = title,
            userPrompt = userPrompt,
            status = JobStatus.QUEUED,
            steps = steps.toMutableList(),
            currentStepIndex = 0,
            progressPercent = 0,
            createdAt = now,
            updatedAt = now,
            executionLogs = mutableListOf(
                if (reused) "تم استرجاع المهمة السابقة باستخدام مفتاح منع التكرار."
                else "تم حفظ المهمة في طابور الخادم. لن يبدأ التنفيذ حتى نشر العامل الخلفي الموثوق."
            )
        )
    }

    suspend fun getJobResult(jobId: String): Map<String, Any?> = withContext(Dispatchers.IO) {
        require(jobId.isNotBlank()) { "Job ID is required." }
        Firebase.auth.currentUser ?: throw IllegalStateException("Sign in before retrieving a job result.")
        val response = functions.getHttpsCallable("getPersistentJobResult")
            .call(mapOf("jobId" to jobId))
            .await()
        @Suppress("UNCHECKED_CAST")
        (response.data as? Map<String, Any?>)
            ?: throw IllegalStateException("Trusted job service returned an invalid result.")
    }

    suspend fun pauseJob(jobId: String) {
        throw UnsupportedOperationException("Pausing is unavailable until the trusted worker supports pause requests.")
    }

    suspend fun resumeJob(jobId: String) {
        throw UnsupportedOperationException("Resuming is unavailable until the trusted worker supports checkpoint resume.")
    }

    suspend fun cancelJob(jobId: String) = withContext(Dispatchers.IO) {
        Firebase.auth.currentUser ?: throw IllegalStateException("Sign in before cancelling a job.")
        functions.getHttpsCallable("cancelPersistentJob")
            .call(mapOf("jobId" to jobId))
            .await()
        Unit
    }

    suspend fun retryJob(jobId: String) {
        throw UnsupportedOperationException("Retry is unavailable until the trusted worker retry policy is deployed.")
    }

    fun getJobsFlow(projectId: String?): Flow<List<PersistentJob>> = flow {
        val user = Firebase.auth.currentUser
        if (user == null) {
            emit(emptyList())
            return@flow
        }
        try {
            val query = if (projectId != null) {
                firestore.collection("persistent_jobs")
                    .whereEqualTo("userId", user.uid)
                    .whereEqualTo("projectId", projectId)
            } else {
                firestore.collection("persistent_jobs")
                    .whereEqualTo("userId", user.uid)
            }

            val snapshot = query.get().await()
            val jobs = snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null
                val stepsRaw = data["stepsData"] as? List<Map<String, Any?>> ?: emptyList()
                val steps = stepsRaw.map { JobStep.fromMap(it) }.toMutableList()
                val logsRaw = data["logs"] as? List<String> ?: emptyList()
                val statusStr = data["status"] as? String
                val status = parsePersistentJobStatus(statusStr)

                PersistentJob(
                    jobId = doc.id,
                    userId = data["userId"] as? String ?: "",
                    projectId = data["projectId"] as? String ?: "",
                    projectName = data["projectName"] as? String ?: "",
                    title = data["title"] as? String ?: "",
                    userPrompt = data["userPrompt"] as? String ?: data["prompt"] as? String ?: "",
                    status = status,
                    steps = steps,
                    currentStepIndex = (data["currentStepIndex"] as? Number)?.toInt() ?: 0,
                    progressPercent = ((data["progressPercent"] ?: data["progress"]) as? Number)?.toInt()?.coerceIn(0, 100) ?: 0,
                    workerId = data["workerId"] as? String,
                    leaseToken = data["leaseToken"] as? String,
                    leaseExpiresAt = (data["leaseExpiresAt"] as? Number)?.toLong(),
                    heartbeatAt = (data["heartbeatAt"] as? Number)?.toLong(),
                    retryCount = (data["retryCount"] as? Number)?.toInt() ?: 0,
                    maxRetries = (data["maxRetries"] as? Number)?.toInt() ?: 3,
                    waitingApprovalId = data["waitingApprovalId"] as? String,
                    totalTokensUsed = (data["totalTokensUsed"] as? Number)?.toInt() ?: 0,
                    estimatedCostUsd = (data["estimatedCostUsd"] as? Number)?.toDouble() ?: 0.0,
                    createdAt = parseFirestoreMillis(data["createdAt"]) ?: 0L,
                    updatedAt = parseFirestoreMillis(data["updatedAt"]) ?: 0L,
                    completedAt = parseFirestoreMillis(data["completedAt"]),
                    executionLogs = logsRaw.toMutableList()
                )
            }.sortedByDescending { it.updatedAt }

            emit(jobs)
        } catch (e: Exception) {
            Log.e("PersistentJobRepo", "Failed to fetch jobs: ${e.message}")
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    fun generateDefaultPipelineSteps(jobId: String, prompt: String): List<JobStep> {
        return listOf(
            JobStep(
                stepIndex = 0,
                title = "فحص المشروع وتحليل المتطلبات",
                description = "تحليل كود المشروع، وفحص ملفات التكوين والتبعيات بناءً على الطلب: $prompt",
                stepType = JobStepType.ANALYSIS,
                assignedRole = AgentRole.REQUIREMENTS_ANALYSIS
            ),
            JobStep(
                stepIndex = 1,
                title = "إصلاح الأخطاء وتحديث الشيفرة",
                description = "تطبيق التعديلات البرمجية وحل التناقضات وتحديث الدوال.",
                stepType = JobStepType.CODE_FIX,
                assignedRole = AgentRole.CODE_DEVELOPER
            ),
            JobStep(
                stepIndex = 2,
                title = "المراجعة والفحص الأمني",
                description = "تدقيق الكود ضد ثغرات الأمان والتحقق من عدم تسريب الأسرار.",
                stepType = JobStepType.SECURITY_REVIEW,
                assignedRole = AgentRole.SECURITY_AUDITOR
            ),
            JobStep(
                stepIndex = 3,
                title = "تشغيل الاختبارات والتحقق الآلي",
                description = "تشغيل وحدات الاختبارات والتأكد من استقرار كافة المكونات.",
                stepType = JobStepType.TESTING,
                assignedRole = AgentRole.TESTING_QA
            ),
            JobStep(
                stepIndex = 4,
                title = "بوابة الموافقة البشرية على النشر",
                description = "طلب موافقة صريحة موقوتة (15 دقيقة) قبل رفع الكود إلى المستودع الرئيسي.",
                stepType = JobStepType.APPROVAL_GATE,
                assignedRole = AgentRole.SECURITY_AUDITOR,
                isApprovalRequired = true
            ),
            JobStep(
                stepIndex = 5,
                title = "رفع التغييرات إلى GitHub",
                description = "إنشاء Commit ذري ودفع التعديلات إلى فرع المشروع المحدد دون تكرار.",
                stepType = JobStepType.GIT_PUSH,
                assignedRole = AgentRole.CODE_DEVELOPER,
                isApprovalRequired = true
            ),
            JobStep(
                stepIndex = 6,
                title = "توليد التقرير النهائي والإشعار",
                description = "إرسال ملخص بالعمليات والملفات المعدلة عبر Telegram وإشعارات المنصة.",
                stepType = JobStepType.NOTIFY,
                assignedRole = AgentRole.DOCUMENTATION
            )
        )
    }

    private fun formatTime(millis: Long): String {
        val sdf = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(millis))
    }
}
