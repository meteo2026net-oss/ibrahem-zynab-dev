package com.example.data.repository

import android.content.Context
import android.util.Log
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.R
import com.example.data.model.AgentRole
import com.example.data.model.JobStatus
import com.example.data.model.JobStep
import com.example.data.model.JobStepType
import com.example.data.model.PersistentJob
import com.example.data.worker.PersistentJobWorker
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.TimeUnit

class PersistentJobRepository(
    private val context: Context
) {
    private val firestore by lazy {
        val databaseId = context.getString(R.string.firestore_database_id)
        Firebase.firestore(databaseId)
    }

    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    /**
     * Creates a new durable job, persists it to Firestore, and enqueues a WorkManager task
     * that will run in the background even if the app, browser, or screen is turned off.
     */
    suspend fun createAndScheduleJob(
        projectId: String,
        projectName: String,
        title: String,
        userPrompt: String,
        customSteps: List<JobStep>? = null
    ): PersistentJob = withContext(Dispatchers.IO) {
        val user = Firebase.auth.currentUser
        val userId = user?.uid ?: "local_worker"
        val jobId = UUID.randomUUID().toString()

        val steps = customSteps ?: generateDefaultPipelineSteps(jobId, userPrompt)

        val job = PersistentJob(
            jobId = jobId,
            userId = userId,
            projectId = projectId,
            projectName = projectName,
            title = title,
            userPrompt = userPrompt,
            status = JobStatus.QUEUED,
            steps = steps.toMutableList(),
            currentStepIndex = 0,
            progressPercent = 0,
            executionLogs = mutableListOf(
                "[${formatTime(System.currentTimeMillis())}] تم إنشاء المهمة الدائمة وجدولتها في نظام التنفيذ الخلفي المستمر."
            )
        )

        // 1. Save to persistent database
        firestore.collection("persistent_jobs").document(jobId)
            .set(job.toFirestoreMap())
            .await()

        // 2. Schedule via WorkManager
        enqueueWorkManagerTask(job)

        job
    }

    private fun enqueueWorkManagerTask(job: PersistentJob) {
        val inputData = Data.Builder()
            .putString(PersistentJobWorker.KEY_JOB_ID, job.jobId)
            .putString(PersistentJobWorker.KEY_JOB_TITLE, job.title)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<PersistentJobWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS)
            .addTag("metra_job")
            .addTag("project_${job.projectId}")
            .build()

        workManager.enqueueUniqueWork(
            "job_${job.jobId}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    suspend fun pauseJob(jobId: String) = withContext(Dispatchers.IO) {
        firestore.collection("persistent_jobs").document(jobId)
            .update(
                mapOf(
                    "status" to JobStatus.PAUSED.id,
                    "updatedAt" to System.currentTimeMillis()
                )
            ).await()
        workManager.cancelUniqueWork("job_$jobId")
    }

    suspend fun resumeJob(jobId: String) = withContext(Dispatchers.IO) {
        val doc = firestore.collection("persistent_jobs").document(jobId).get().await()
        if (doc.exists()) {
            val data = doc.data ?: return@withContext
            val stepsRaw = data["stepsData"] as? List<Map<String, Any?>> ?: emptyList()
            val steps = stepsRaw.map { JobStep.fromMap(it) }.toMutableList()
            val logsRaw = data["logs"] as? List<String> ?: emptyList()

            val job = PersistentJob(
                jobId = jobId,
                userId = data["userId"] as? String ?: "",
                projectId = data["projectId"] as? String ?: "",
                projectName = data["projectName"] as? String ?: "",
                title = data["title"] as? String ?: "",
                userPrompt = data["userPrompt"] as? String ?: "",
                status = JobStatus.QUEUED,
                steps = steps,
                currentStepIndex = (data["currentStepIndex"] as? Number)?.toInt() ?: 0,
                progressPercent = (data["progressPercent"] as? Number)?.toInt() ?: 0,
                totalTokensUsed = (data["totalTokensUsed"] as? Number)?.toInt() ?: 0,
                estimatedCostUsd = (data["estimatedCostUsd"] as? Number)?.toDouble() ?: 0.0,
                createdAt = (data["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                executionLogs = logsRaw.toMutableList()
            )

            job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] تم استئناف المهمة من نقطة التوقف الأخيرة (${job.currentStepIndex + 1}/${job.steps.size}).")

            firestore.collection("persistent_jobs").document(jobId)
                .set(job.toFirestoreMap())
                .await()

            enqueueWorkManagerTask(job)
        }
    }

    suspend fun cancelJob(jobId: String) = withContext(Dispatchers.IO) {
        firestore.collection("persistent_jobs").document(jobId)
            .update(
                mapOf(
                    "status" to JobStatus.CANCELLED.id,
                    "completedAt" to System.currentTimeMillis(),
                    "updatedAt" to System.currentTimeMillis()
                )
            ).await()
        workManager.cancelUniqueWork("job_$jobId")
    }

    suspend fun retryJob(jobId: String) = withContext(Dispatchers.IO) {
        resumeJob(jobId)
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
                val statusStr = data["status"] as? String ?: "queued"
                val status = JobStatus.values().find { it.id == statusStr } ?: JobStatus.QUEUED

                PersistentJob(
                    jobId = doc.id,
                    userId = data["userId"] as? String ?: "",
                    projectId = data["projectId"] as? String ?: "",
                    projectName = data["projectName"] as? String ?: "",
                    title = data["title"] as? String ?: "",
                    userPrompt = data["userPrompt"] as? String ?: "",
                    status = status,
                    steps = steps,
                    currentStepIndex = (data["currentStepIndex"] as? Number)?.toInt() ?: 0,
                    progressPercent = (data["progressPercent"] as? Number)?.toInt() ?: 0,
                    workerId = data["workerId"] as? String,
                    leaseToken = data["leaseToken"] as? String,
                    leaseExpiresAt = (data["leaseExpiresAt"] as? Number)?.toLong(),
                    heartbeatAt = (data["heartbeatAt"] as? Number)?.toLong(),
                    retryCount = (data["retryCount"] as? Number)?.toInt() ?: 0,
                    maxRetries = (data["maxRetries"] as? Number)?.toInt() ?: 3,
                    waitingApprovalId = data["waitingApprovalId"] as? String,
                    totalTokensUsed = (data["totalTokensUsed"] as? Number)?.toInt() ?: 0,
                    estimatedCostUsd = (data["estimatedCostUsd"] as? Number)?.toDouble() ?: 0.0,
                    createdAt = (data["createdAt"] as? Number)?.toLong() ?: 0L,
                    updatedAt = (data["updatedAt"] as? Number)?.toLong() ?: 0L,
                    completedAt = (data["completedAt"] as? Number)?.toLong(),
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
