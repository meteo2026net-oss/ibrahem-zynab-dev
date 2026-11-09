package com.example.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Process
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.R
import com.example.data.orchestrator.JobExecutionResult
import com.example.data.orchestrator.TaskOrchestrator
import com.example.data.repository.AiProvidersRepository
import com.example.data.repository.AutomationRepository
import com.example.data.repository.IntegrationsRepository
import com.example.data.repository.ProjectRepository
import com.example.data.repository.StorageRepository
import java.util.UUID

class PersistentJobWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val KEY_JOB_ID = "JOB_ID"
        const val KEY_JOB_TITLE = "JOB_TITLE"
        const val NOTIFICATION_CHANNEL_ID = "metra_persistent_worker"
        const val NOTIFICATION_ID = 4040
    }

    override suspend fun doWork(): Result {
        val jobId = inputData.getString(KEY_JOB_ID) ?: return Result.failure()
        val jobTitle = inputData.getString(KEY_JOB_TITLE) ?: "مهمة خلفية جارية"

        // Setup notification channel and foreground service
        createNotificationChannel()
        try {
            setForeground(createForegroundInfo(jobTitle))
        } catch (e: Exception) {
            // In Android 12+, setForeground may fail if app in background, but worker continues running
        }

        val projectRepository = ProjectRepository(applicationContext)
        val integrationsRepository = IntegrationsRepository(applicationContext)
        val storageRepository = StorageRepository(applicationContext)
        val automationRepository = AutomationRepository(
            applicationContext,
            projectRepository,
            storageRepository,
            integrationsRepository
        )
        val aiProvidersRepository = AiProvidersRepository(
            applicationContext,
            integrationsRepository
        )

        val orchestrator = TaskOrchestrator(
            applicationContext,
            aiProvidersRepository,
            integrationsRepository,
            automationRepository
        )

        val workerId = "worker-${Build.MODEL}-${Process.myPid()}"
        val leaseToken = UUID.randomUUID().toString()

        return when (val result = orchestrator.executeJob(jobId, workerId, leaseToken)) {
            is JobExecutionResult.Success -> Result.success()
            is JobExecutionResult.PausedForApproval -> Result.success()
            is JobExecutionResult.PausedByUser -> Result.success()
            is JobExecutionResult.RetryScheduled -> Result.retry()
            is JobExecutionResult.Failed -> Result.failure()
        }
    }

    private fun createForegroundInfo(jobTitle: String): ForegroundInfo {
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("المطور التقني ابراهيم زينب - محرك التشغيل الدائم")
            .setContentText("جاري تنفيذ: $jobTitle")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "METRA Background Worker"
            val descriptionText = "قناة إشعارات تشغيل المهام الخلفية الدائمة"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
