package com.example.data.orchestrator

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.AgentRole
import com.example.data.model.ApprovalOperation
import com.example.data.model.ApprovalStatus
import com.example.data.model.IdempotencyRecord
import com.example.data.model.InteractiveApprovalRequest
import com.example.data.model.JobStatus
import com.example.data.model.JobStep
import com.example.data.model.JobStepType
import com.example.data.model.NotificationType
import com.example.data.model.PersistentJob
import com.example.data.repository.AiProvidersRepository
import com.example.data.repository.AutomationRepository
import com.example.data.repository.IntegrationsRepository
import com.example.data.security.CryptoUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

sealed class JobExecutionResult {
    data class Success(val job: PersistentJob) : JobExecutionResult()
    data class PausedForApproval(val job: PersistentJob, val approvalRequestId: String) : JobExecutionResult()
    data class PausedByUser(val job: PersistentJob) : JobExecutionResult()
    data class RetryScheduled(val job: PersistentJob, val error: String) : JobExecutionResult()
    data class Failed(val job: PersistentJob, val error: String) : JobExecutionResult()
}

class TaskOrchestrator(
    private val context: Context,
    private val aiProvidersRepository: AiProvidersRepository,
    private val integrationsRepository: IntegrationsRepository,
    private val automationRepository: AutomationRepository
) {
    private val firestore by lazy {
        val databaseId = context.getString(R.string.firestore_database_id)
        Firebase.firestore(databaseId)
    }

    /**
     * Executes a persistent job step-by-step with worker leasing, heartbeats,
     * checkpointing, idempotency checks, and approval gates.
     */
    suspend fun executeJob(
        jobId: String,
        workerId: String,
        leaseToken: String
    ): JobExecutionResult = withContext(Dispatchers.IO) {
        val user = Firebase.auth.currentUser
        val userId = user?.uid ?: "local_worker"

        // 1. Fetch current job state from persistent database
        val jobDoc = firestore.collection("persistent_jobs").document(jobId).get().await()
        if (!jobDoc.exists()) {
            return@withContext JobExecutionResult.Failed(
                PersistentJob(jobId = jobId, userId = userId, projectId = "", projectName = "", title = "", userPrompt = ""),
                "Job not found in database: $jobId"
            )
        }

        val jobData = jobDoc.data ?: emptyMap<String, Any?>()
        val job = deserializeJob(jobId, jobData)

        // 2. Check and acquire/renew worker lease
        val now = System.currentTimeMillis()
        if (job.status == JobStatus.PAUSED || job.status == JobStatus.CANCELLED) {
            return@withContext JobExecutionResult.PausedByUser(job)
        }

        // Lease duration: 5 minutes
        val leaseDurationMs = 5 * 60 * 1000L
        job.workerId = workerId
        job.leaseToken = leaseToken
        job.leaseExpiresAt = now + leaseDurationMs
        job.heartbeatAt = now
        job.status = JobStatus.RUNNING
        job.updatedAt = now
        job.executionLogs.add("[${formatTime(now)}] Worker $workerId acquired durable lease (expires in 5m). Resuming orchestration...")
        saveJobState(job)

        // 3. Execution loop over steps
        val steps = job.steps
        for (i in job.currentStepIndex until steps.size) {
            val step = steps[i]

            // Check if job was paused or cancelled in between steps
            val freshCheck = firestore.collection("persistent_jobs").document(jobId).get().await()
            val freshStatus = freshCheck.getString("status")
            if (freshStatus == JobStatus.PAUSED.id) {
                job.status = JobStatus.PAUSED
                saveJobState(job)
                return@withContext JobExecutionResult.PausedByUser(job)
            }
            if (freshStatus == JobStatus.CANCELLED.id) {
                job.status = JobStatus.CANCELLED
                saveJobState(job)
                return@withContext JobExecutionResult.Failed(job, "Job was cancelled by user")
            }

            // Renew worker heartbeat
            renewHeartbeat(job, workerId, leaseToken)

            // Skip step if already marked completed in checkpoint
            if (step.status == JobStatus.COMPLETED && !step.checkpointOutput.isNullOrBlank()) {
                job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1}/${steps.size}: '${step.title}' already completed from checkpoint.")
                continue
            }

            // 4. Handle Human Approval Gate
            if (step.isApprovalRequired || step.stepType == JobStepType.APPROVAL_GATE) {
                val isApproved = checkExistingApproval(job.projectId, step.stepId)
                if (!isApproved) {
                    val approval = createApprovalRequestForStep(job, step)
                    job.status = JobStatus.WAITING_FOR_APPROVAL
                    job.waitingApprovalId = approval.id
                    step.status = JobStatus.WAITING_FOR_APPROVAL
                    job.currentStepIndex = i
                    job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1}/${steps.size}: Requires human approval for ${step.title}. Paused at checkpoint.")
                    saveJobState(job)

                    // Dispatch notifications
                    automationRepository.dispatchNotification(
                        projectId = job.projectId,
                        title = "موافقة مطلوبة للمهمة: ${job.title}",
                        message = "تتطلب الخطوة '${step.title}' موافقتك الصريحة للمتابعة. تنتهي الصلاحية بعد 15 دقيقة.",
                        type = NotificationType.APPROVAL_NEEDED
                    )

                    return@withContext JobExecutionResult.PausedForApproval(job, approval.id)
                } else {
                    job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1}/${steps.size}: Human approval verified for '${step.title}'. Proceeding.")
                }
            }

            // 5. Check Idempotency for sensitive external actions
            val idempotencyKey = step.idempotencyKey ?: CryptoUtils.calculateSha256("${job.jobId}:${step.stepId}:${step.stepType.id}".toByteArray())
            if (step.stepType.isSensitive) {
                val existingRecord = checkIdempotency(userId, idempotencyKey)
                if (existingRecord != null && existingRecord.status == "SUCCESS") {
                    step.status = JobStatus.COMPLETED
                    step.checkpointOutput = existingRecord.resultPayload
                    step.completedAt = existingRecord.executedAt
                    job.currentStepIndex = i + 1
                    job.progressPercent = ((i + 1) * 100) / steps.size
                    job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1}/${steps.size}: Idempotency verified. Skipped duplicate sensitive action '${step.title}'.")
                    saveCheckpoint(job, step)
                    saveJobState(job)
                    continue
                }
            }

            // 6. Execute Step via AI Agent Engine
            step.status = JobStatus.RUNNING
            step.startedAt = System.currentTimeMillis()
            job.currentStepIndex = i
            job.progressPercent = (i * 100) / steps.size
            job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1}/${steps.size}: Executing '${step.title}' using agent [${step.assignedRole.titleAr}]...")
            saveJobState(job)

            try {
                val stepOutput = executeStepAction(job, step)

                step.status = JobStatus.COMPLETED
                step.checkpointOutput = stepOutput.content
                step.tokensUsed = stepOutput.inputTokens + stepOutput.outputTokens
                step.costUsd = stepOutput.estimatedCostUsd
                step.completedAt = System.currentTimeMillis()

                job.totalTokensUsed += step.tokensUsed
                job.estimatedCostUsd += step.costUsd
                job.currentStepIndex = i + 1
                job.progressPercent = ((i + 1) * 100) / steps.size
                job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1}/${steps.size} completed (${stepOutput.modelUsed}${if (stepOutput.isFailover) " [FAILOVER]" else ""}). Checkpoint saved.")

                // Record idempotency on sensitive action completion
                if (step.stepType.isSensitive) {
                    recordIdempotency(userId, idempotencyKey, step.stepType.id, stepOutput.content)
                }

                saveCheckpoint(job, step)
                saveJobState(job)

            } catch (e: Exception) {
                Log.e("TaskOrchestrator", "Error in step ${step.title}: ${e.message}", e)
                step.status = JobStatus.FAILED
                step.errorMessage = e.localizedMessage ?: "Unknown execution error"
                job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] Step ${i + 1} failed: ${step.errorMessage}")

                if (job.retryCount < job.maxRetries) {
                    job.retryCount++
                    job.status = JobStatus.WAITING_FOR_RETRY
                    saveJobState(job)
                    return@withContext JobExecutionResult.RetryScheduled(job, step.errorMessage ?: "Retryable error")
                } else {
                    job.status = JobStatus.FAILED
                    job.completedAt = System.currentTimeMillis()
                    saveJobState(job)
                    automationRepository.dispatchNotification(
                        projectId = job.projectId,
                        title = "فشلت المهمة: ${job.title}",
                        message = "تعذر إكمال الخطوة '${step.title}': ${step.errorMessage}",
                        type = NotificationType.TASK_COMPLETED,
                        severity = "WARNING"
                    )
                    return@withContext JobExecutionResult.Failed(job, step.errorMessage ?: "Job failed after retries")
                }
            }
        }

        // 7. Complete entire job
        job.status = JobStatus.COMPLETED
        job.progressPercent = 100
        job.completedAt = System.currentTimeMillis()
        job.leaseToken = null
        job.leaseExpiresAt = null
        job.executionLogs.add("[${formatTime(System.currentTimeMillis())}] All ${steps.size} steps completed successfully. Durable job completed.")
        saveJobState(job)

        // Dispatch completion notification
        automationRepository.dispatchNotification(
            projectId = job.projectId,
            title = "اكتملت المهمة بنجاح: ${job.title}",
            message = "تم تنفيذ جميع الخطوات (${steps.size} خطوات) بنجاح واكتمال نقاط التحقق.",
            type = NotificationType.TASK_COMPLETED
        )

        JobExecutionResult.Success(job)
    }

    private suspend fun executeStepAction(job: PersistentJob, step: JobStep) = withContext(Dispatchers.IO) {
        val prompt = buildString {
            append("المشروع: ${job.projectName}\n")
            append("الهدف العام للمهمة: ${job.userPrompt}\n")
            append("المرحلة الحالية: ${step.title}\n")
            append("وصف الخطوة: ${step.description}\n")
            if (job.currentStepIndex > 0) {
                val prevStep = job.steps.getOrNull(job.currentStepIndex - 1)
                if (prevStep?.checkpointOutput != null) {
                    append("مخرجات الخطوة السابقة:\n${prevStep.checkpointOutput}\n")
                }
            }
        }

        aiProvidersRepository.generateWithFailover(
            projectId = job.projectId,
            jobId = job.jobId,
            stepIndex = step.stepIndex,
            role = step.assignedRole,
            userPrompt = prompt,
            systemPrompt = "أنت وكيل تطوير برمجيات خبير في منصة المطور التقني ابراهيم زينب. قم بتنفيذ الخطوة بدقة واحترافية عالية مع تقديم شيفرات وحلول نظيفة وقابلة للتنفيذ."
        )
    }

    private suspend fun renewHeartbeat(job: PersistentJob, workerId: String, leaseToken: String) {
        try {
            val now = System.currentTimeMillis()
            job.heartbeatAt = now
            job.leaseExpiresAt = now + (5 * 60 * 1000L)
            firestore.collection("persistent_jobs").document(job.jobId)
                .update(
                    mapOf(
                        "heartbeatAt" to now,
                        "leaseExpiresAt" to job.leaseExpiresAt,
                        "workerId" to workerId,
                        "leaseToken" to leaseToken
                    )
                ).await()
        } catch (e: Exception) {
            Log.w("TaskOrchestrator", "Failed to renew heartbeat: ${e.message}")
        }
    }

    private suspend fun saveCheckpoint(job: PersistentJob, step: JobStep) {
        try {
            val checkpointId = "${job.jobId}_step_${step.stepIndex}"
            val map = mapOf(
                "userId" to job.userId,
                "jobId" to job.jobId,
                "stepIndex" to step.stepIndex,
                "stepName" to step.title,
                "agentRole" to step.assignedRole.id,
                "status" to step.status.id,
                "outputPayload" to (step.checkpointOutput ?: ""),
                "tokensUsed" to step.tokensUsed,
                "costUsd" to step.costUsd,
                "completedAt" to (step.completedAt ?: System.currentTimeMillis())
            )
            firestore.collection("job_checkpoints").document(checkpointId).set(map).await()
        } catch (e: Exception) {
            Log.e("TaskOrchestrator", "Failed to save checkpoint: ${e.message}")
        }
    }

    private suspend fun saveJobState(job: PersistentJob) {
        try {
            firestore.collection("persistent_jobs").document(job.jobId)
                .set(job.toFirestoreMap())
                .await()
        } catch (e: Exception) {
            Log.e("TaskOrchestrator", "Failed to save job state: ${e.message}")
        }
    }

    private suspend fun checkExistingApproval(projectId: String, stepId: String): Boolean {
        return try {
            val snapshot = firestore.collection("approval_requests")
                .whereEqualTo("projectId", projectId)
                .whereEqualTo("targetEntityId", stepId)
                .whereEqualTo("status", "APPROVED")
                .get()
                .await()
            !snapshot.isEmpty
        } catch (e: Exception) {
            false
        }
    }

    private suspend fun createApprovalRequestForStep(job: PersistentJob, step: JobStep): InteractiveApprovalRequest {
        val op = when (step.stepType) {
            JobStepType.GIT_PUSH -> ApprovalOperation.PUSH_COMMIT
            JobStepType.DEPLOY -> ApprovalOperation.DEPLOY
            else -> ApprovalOperation.CHANGE_SETTINGS
        }

        val approvalToken = UUID.randomUUID().toString()
        val request = InteractiveApprovalRequest(
            id = UUID.randomUUID().toString(),
            userId = job.userId,
            projectId = job.projectId,
            projectName = job.projectName,
            operationType = op.name,
            description = "الموافقة على تنفيذ '${step.title}' ضمن المهمة [${job.title}]",
            approvalToken = approvalToken,
            status = ApprovalStatus.PENDING.name,
            expiresAt = System.currentTimeMillis() + (15 * 60 * 1000L),
            requestedBy = "Worker / Orchestrator"
        )

        val map = mapOf(
            "userId" to request.userId,
            "projectId" to request.projectId,
            "projectName" to request.projectName,
            "operationType" to request.operationType,
            "description" to request.description,
            "approvalToken" to request.approvalToken,
            "status" to request.status,
            "expiresAt" to request.expiresAt,
            "requestedBy" to request.requestedBy,
            "targetEntityId" to step.stepId,
            "createdAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
        )

        firestore.collection("approval_requests").document(request.id)
            .set(map)
            .await()

        return request
    }

    private suspend fun checkIdempotency(userId: String, key: String): IdempotencyRecord? {
        return try {
            val doc = firestore.collection("idempotency_records").document(key).get().await()
            if (doc.exists()) {
                val data = doc.data ?: return null
                IdempotencyRecord(
                    idempotencyKey = key,
                    userId = data["userId"] as? String ?: userId,
                    actionType = data["actionType"] as? String ?: "",
                    status = data["status"] as? String ?: "",
                    resultPayload = data["resultPayload"] as? String ?: "",
                    executedAt = (data["executedAt"] as? Number)?.toLong() ?: 0L
                )
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun recordIdempotency(userId: String, key: String, actionType: String, payload: String) {
        try {
            val record = IdempotencyRecord(
                idempotencyKey = key,
                userId = userId,
                actionType = actionType,
                status = "SUCCESS",
                resultPayload = payload,
                executedAt = System.currentTimeMillis()
            )
            firestore.collection("idempotency_records").document(key).set(record.toFirestoreMap()).await()
        } catch (e: Exception) {
            Log.w("TaskOrchestrator", "Failed to record idempotency: ${e.message}")
        }
    }

    private fun deserializeJob(jobId: String, data: Map<String, Any?>): PersistentJob {
        val stepsRaw = data["stepsData"] as? List<Map<String, Any?>> ?: emptyList()
        val steps = stepsRaw.map { JobStep.fromMap(it) }.toMutableList()
        val logsRaw = data["logs"] as? List<String> ?: emptyList()

        val statusStr = data["status"] as? String ?: "queued"
        val status = JobStatus.values().find { it.id == statusStr } ?: JobStatus.QUEUED

        return PersistentJob(
            jobId = jobId,
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
            createdAt = (data["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
            updatedAt = (data["updatedAt"] as? Number)?.toLong() ?: System.currentTimeMillis(),
            completedAt = (data["completedAt"] as? Number)?.toLong(),
            executionLogs = logsRaw.toMutableList()
        )
    }

    private fun formatTime(millis: Long): String {
        val sdf = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
        return sdf.format(java.util.Date(millis))
    }
}
