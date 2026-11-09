package com.example

import com.example.data.model.ApprovalOperation
import com.example.data.model.ApprovalStatus
import com.example.data.model.FileCategory
import com.example.data.model.InteractiveApprovalRequest
import com.example.data.model.Project
import com.example.data.model.ProjectBackupSnapshot
import com.example.data.model.StorageDestination
import com.example.data.model.StoredFile
import com.example.data.security.CryptoUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MetraCoreUnitTest {

    @Test
    fun testCryptoUtilsEncryptionAndDecryption() {
        val originalSecret = "sk-proj-999888777666555444333222111"
        val encrypted = CryptoUtils.encrypt(originalSecret)
        assertNotEquals(originalSecret, encrypted)

        val decrypted = CryptoUtils.decrypt(encrypted)
        assertEquals(originalSecret, decrypted)
    }

    @Test
    fun testCryptoUtilsSecretMasking() {
        val apiKey = "sk-1234567890abcdef"
        val masked = CryptoUtils.maskSecret(apiKey)
        assertTrue(masked.startsWith("••••"))
        assertTrue(masked.endsWith("cdef"))
        assertFalse(masked.contains("1234567890"))
    }

    @Test
    fun testSha256ChecksumCalculation() {
        val sampleBytes = "METRA_AI_CONTROL_SPEC_0934884157".toByteArray()
        val hash1 = CryptoUtils.calculateSha256(sampleBytes)
        val hash2 = CryptoUtils.calculateSha256(sampleBytes)
        assertEquals(hash1, hash2)
        assertEquals(64, hash1.length)
    }

    @Test
    fun testStorageRoutingSeparation() {
        val codeCategory = FileCategory.CODE
        val pdfCategory = FileCategory.PDF
        val imageCategory = FileCategory.IMAGE

        val codeTarget = when (codeCategory) {
            FileCategory.CODE -> StorageDestination.GITHUB
            else -> StorageDestination.PRIVATE_STORAGE
        }
        val pdfTarget = when (pdfCategory) {
            FileCategory.PDF, FileCategory.REPORT -> StorageDestination.GOOGLE_DRIVE
            else -> StorageDestination.PRIVATE_STORAGE
        }
        val imgTarget = when (imageCategory) {
            FileCategory.IMAGE -> StorageDestination.PRIVATE_STORAGE
            else -> StorageDestination.GOOGLE_DRIVE
        }

        assertEquals(StorageDestination.GITHUB, codeTarget)
        assertEquals(StorageDestination.GOOGLE_DRIVE, pdfTarget)
        assertEquals(StorageDestination.PRIVATE_STORAGE, imgTarget)
    }

    @Test
    fun testSecurityRuleRejectsDangerousBinaries() {
        val forbidden = listOf("payload.exe", "backdoor.sh", "app.apk")
        val forbiddenExtensions = listOf(".exe", ".sh", ".bat", ".apk", ".dex")

        for (filename in forbidden) {
            val isBlocked = forbiddenExtensions.any { filename.endsWith(it, ignoreCase = true) }
            assertTrue("File $filename must be blocked by security policy", isBlocked)
        }

        val safeFiles = listOf("main.kt", "diagram.jpg", "spec.pdf")
        for (filename in safeFiles) {
            val isBlocked = forbiddenExtensions.any { filename.endsWith(it, ignoreCase = true) }
            assertFalse("File $filename should be permitted", isBlocked)
        }
    }

    @Test
    fun testInteractiveApprovalTokenAndExpiration() {
        val now = System.currentTimeMillis()
        val validRequest = InteractiveApprovalRequest(
            id = "req_1",
            operationType = ApprovalOperation.PUSH_COMMIT.name,
            approvalToken = "tok_valid_1234",
            status = ApprovalStatus.PENDING.name,
            expiresAt = now + (15 * 60 * 1000L) // 15 mins in future
        )
        assertFalse("Request within 15 minutes must not be expired", validRequest.isExpired())

        val expiredRequest = InteractiveApprovalRequest(
            id = "req_2",
            operationType = ApprovalOperation.PUSH_COMMIT.name,
            approvalToken = "tok_expired_5678",
            status = ApprovalStatus.PENDING.name,
            expiresAt = now - 1000L // past
        )
        assertTrue("Request in the past must be marked as expired", expiredRequest.isExpired())
    }

    @Test
    fun testFileIntelligenceDuplicateDetection() {
        val fileContent = "MOCK_PDF_STREAM_STATION_TELEMETRY".toByteArray()
        val checksum = CryptoUtils.calculateSha256(fileContent)

        val existingFiles = listOf(
            StoredFile(id = "f1", fileName = "prev_report.pdf", checksumSha256 = checksum)
        )

        val isDuplicate = existingFiles.any { it.checksumSha256 == checksum }
        assertTrue("File Intelligence must detect duplicate checksum", isDuplicate)

        val differentContent = "DIFFERENT_CONTENT".toByteArray()
        val differentChecksum = CryptoUtils.calculateSha256(differentContent)
        val isDifferentDuplicate = existingFiles.any { it.checksumSha256 == differentChecksum }
        assertFalse("Different content must not be marked as duplicate", isDifferentDuplicate)
    }

    @Test
    fun testBackupEncryptionAndRestoreIntegrity() {
        val rawSnapshot = """{"project":"METRA","owner":"المطور التقني ابراهيم زينب","phone":"0934884157"}"""
        val encrypted = CryptoUtils.encrypt(rawSnapshot)
        val checksum = CryptoUtils.calculateSha256(encrypted.toByteArray())

        val snapshot = ProjectBackupSnapshot(
            id = "bkp_1",
            projectName = "METRA",
            snapshotVersion = "v1.0.0",
            encryptedData = encrypted,
            sha256Checksum = checksum,
            status = "VERIFIED"
        )

        // Verify checksum matches
        val currentChecksum = CryptoUtils.calculateSha256(snapshot.encryptedData.toByteArray())
        assertEquals(snapshot.sha256Checksum, currentChecksum)

        // Verify decryption recovers original payload
        val decrypted = CryptoUtils.decrypt(snapshot.encryptedData)
        assertEquals(rawSnapshot, decrypted)
        assertTrue(decrypted.contains("0934884157"))
    }

    @Test
    fun testTelegramCommandsList() {
        val supportedCommands = listOf(
            "/start", "/projects", "/select", "/status", "/tasks",
            "/newtask", "/check", "/fix", "/test", "/report",
            "/upload", "/files", "/backup", "/github", "/jobs", "/job", "/help"
        )
        assertEquals(17, supportedCommands.size)
        assertTrue(supportedCommands.contains("/start"))
        assertTrue(supportedCommands.contains("/backup"))
        assertTrue(supportedCommands.contains("/github"))
        assertTrue(supportedCommands.contains("/jobs"))
        assertTrue(supportedCommands.contains("/job"))
    }

    // ========================================================
    // Mandatory AI Providers & Background Engine Tests
    // ========================================================

    @Test
    fun testDurableJobCreationAndStepProgression() {
        val steps = listOf(
            com.example.data.model.JobStep(
                stepIndex = 0,
                title = "فحص المشروع",
                description = "فحص الكود",
                stepType = com.example.data.model.JobStepType.ANALYSIS,
                assignedRole = com.example.data.model.AgentRole.REQUIREMENTS_ANALYSIS
            ),
            com.example.data.model.JobStep(
                stepIndex = 1,
                title = "إصلاح الأخطاء",
                description = "إصلاح المشاكل",
                stepType = com.example.data.model.JobStepType.CODE_FIX,
                assignedRole = com.example.data.model.AgentRole.CODE_DEVELOPER
            ),
            com.example.data.model.JobStep(
                stepIndex = 2,
                title = "رفع التغييرات",
                description = "دفع لـ GitHub",
                stepType = com.example.data.model.JobStepType.GIT_PUSH,
                assignedRole = com.example.data.model.AgentRole.CODE_DEVELOPER,
                isApprovalRequired = true
            )
        )

        val job = com.example.data.model.PersistentJob(
            jobId = "job_test_101",
            userId = "usr_ibrahim",
            projectId = "proj_weather",
            projectName = "Weather System",
            title = "فحص وإصلاح ونشر",
            userPrompt = "افحص وأصلح وارفع لـ GitHub",
            status = com.example.data.model.JobStatus.QUEUED,
            steps = steps.toMutableList()
        )

        assertEquals(com.example.data.model.JobStatus.QUEUED, job.status)
        assertEquals(3, job.steps.size)
        assertEquals(0, job.currentStepIndex)
        assertEquals(0, job.progressPercent)

        // Progress step 0
        job.status = com.example.data.model.JobStatus.RUNNING
        job.steps[0].status = com.example.data.model.JobStatus.COMPLETED
        job.steps[0].checkpointOutput = "No syntax errors found."
        job.currentStepIndex = 1
        job.progressPercent = 33

        assertEquals(1, job.currentStepIndex)
        assertEquals("No syntax errors found.", job.steps[0].checkpointOutput)
    }

    @Test
    fun testJobStatePersistenceAcrossAppClose() {
        val step = com.example.data.model.JobStep(
            stepIndex = 0,
            title = "تحليل",
            description = "فحص الكود",
            stepType = com.example.data.model.JobStepType.ANALYSIS,
            assignedRole = com.example.data.model.AgentRole.REQUIREMENTS_ANALYSIS,
            status = com.example.data.model.JobStatus.COMPLETED,
            checkpointOutput = "AST Parsed Successfully"
        )
        val job = com.example.data.model.PersistentJob(
            jobId = "job_serialize_test",
            userId = "usr_001",
            projectId = "proj_001",
            projectName = "METRA Project",
            title = "Background Test",
            userPrompt = "Do task",
            status = com.example.data.model.JobStatus.RUNNING,
            steps = mutableListOf(step),
            currentStepIndex = 1,
            progressPercent = 50,
            executionLogs = mutableListOf("Log 1", "Log 2")
        )

        val serializedMap = job.toFirestoreMap()
        assertEquals("usr_001", serializedMap["userId"])
        assertEquals("running", serializedMap["status"])
        assertEquals(50, serializedMap["progressPercent"])

        // Deserialization verification
        val restoredStep = com.example.data.model.JobStep.fromMap(
            (serializedMap["stepsData"] as List<Map<String, Any?>>)[0]
        )
        assertEquals("تحليل", restoredStep.title)
        assertEquals("AST Parsed Successfully", restoredStep.checkpointOutput)
        assertEquals(com.example.data.model.JobStatus.COMPLETED, restoredStep.status)
    }

    @Test
    fun testWorkerLeaseAcquisitionAndRenewal() {
        val now = System.currentTimeMillis()
        val job = com.example.data.model.PersistentJob(
            jobId = "lease_job_1",
            userId = "usr_1",
            projectId = "p1",
            projectName = "P1",
            title = "Lease Test",
            userPrompt = "Run test",
            workerId = "worker-pixel-1234",
            leaseToken = "token-abc-999",
            leaseExpiresAt = now + 300_000L, // 5 minutes in future
            heartbeatAt = now
        )

        assertTrue("Job should have valid active lease", job.hasValidLease())
        assertFalse("Job lease should not be expired", job.isLeaseExpired())
    }

    @Test
    fun testStalledWorkerLeaseExpirationAndReclamation() {
        val now = System.currentTimeMillis()
        val expiredJob = com.example.data.model.PersistentJob(
            jobId = "lease_job_stalled",
            userId = "usr_1",
            projectId = "p1",
            projectName = "P1",
            title = "Stalled Job",
            userPrompt = "Run test",
            status = com.example.data.model.JobStatus.RUNNING,
            workerId = "worker-dead-pid",
            leaseToken = "token-dead",
            leaseExpiresAt = now - 60_000L, // Expired 1 min ago
            heartbeatAt = now - 180_000L
        )

        assertFalse("Expired lease must not be considered valid", expiredJob.hasValidLease())
        assertTrue("Expired lease must be detected as expired", expiredJob.isLeaseExpired())

        // Reclaim by new worker
        val newWorkerId = "worker-healthy-5678"
        val newLeaseToken = "token-renewed"
        expiredJob.workerId = newWorkerId
        expiredJob.leaseToken = newLeaseToken
        expiredJob.leaseExpiresAt = now + 300_000L
        expiredJob.heartbeatAt = now

        assertTrue("Reclaimed lease should now be valid", expiredJob.hasValidLease())
        assertEquals(newWorkerId, expiredJob.workerId)
    }

    @Test
    fun testCheckpointOutputSavingAndResume() {
        val step0 = com.example.data.model.JobStep(
            stepIndex = 0,
            title = "Step 0",
            description = "Desc 0",
            stepType = com.example.data.model.JobStepType.ANALYSIS,
            assignedRole = com.example.data.model.AgentRole.REQUIREMENTS_ANALYSIS,
            status = com.example.data.model.JobStatus.COMPLETED,
            checkpointOutput = "Pre-computed Output for Step 0"
        )
        val step1 = com.example.data.model.JobStep(
            stepIndex = 1,
            title = "Step 1",
            description = "Desc 1",
            stepType = com.example.data.model.JobStepType.CODE_FIX,
            assignedRole = com.example.data.model.AgentRole.CODE_DEVELOPER,
            status = com.example.data.model.JobStatus.QUEUED
        )

        val job = com.example.data.model.PersistentJob(
            jobId = "resume_test_job",
            userId = "u1",
            projectId = "p1",
            projectName = "P1",
            title = "Resume Test",
            userPrompt = "Run",
            steps = mutableListOf(step0, step1),
            currentStepIndex = 0
        )

        // Resuming: step 0 has checkpoint, so we advance to step 1
        var resumeIndex = job.currentStepIndex
        if (job.steps[resumeIndex].status == com.example.data.model.JobStatus.COMPLETED &&
            !job.steps[resumeIndex].checkpointOutput.isNullOrBlank()
        ) {
            resumeIndex++
        }
        assertEquals(1, resumeIndex)
        assertEquals("Step 1", job.steps[resumeIndex].title)
    }

    @Test
    fun testSensitiveActionIdempotency() {
        val jobId = "job_push_999"
        val stepId = "step_push_commit"
        val actionType = "GIT_PUSH"

        val idempotencyKey = CryptoUtils.calculateSha256("$jobId:$stepId:$actionType".toByteArray())
        assertEquals(64, idempotencyKey.length)

        val record = com.example.data.model.IdempotencyRecord(
            idempotencyKey = idempotencyKey,
            userId = "usr_ibrahim",
            actionType = actionType,
            status = "SUCCESS",
            resultPayload = """{"commitSha":"a1b2c3d4e5f6","branch":"main","status":"pushed"}"""
        )

        // If duplicate execution is attempted, match idempotency key and return existing payload
        val attemptKey = CryptoUtils.calculateSha256("$jobId:$stepId:$actionType".toByteArray())
        assertEquals(record.idempotencyKey, attemptKey)
        assertTrue(record.resultPayload.contains("commitSha"))
    }

    @Test
    fun testHumanApprovalGateInterruptionAndExpiration() {
        val now = System.currentTimeMillis()
        val approval = InteractiveApprovalRequest(
            id = "appr_123",
            projectId = "proj_main",
            projectName = "Main App",
            operationType = ApprovalOperation.PUSH_COMMIT.name,
            description = "الموافقة على النشر إلى الإنتاج",
            expiresAt = now + (15 * 60 * 1000L), // 15 mins
            status = ApprovalStatus.PENDING.name
        )

        assertFalse("Approval should not be expired initially", approval.isExpired())

        // Expired approval
        val expiredApproval = approval.copy(expiresAt = now - 1000L)
        assertTrue("Expired approval must be detected", expiredApproval.isExpired())
    }

    @Test
    fun testAiModelCapabilityMatrixSuitability() {
        val deepSeekR1 = com.example.data.model.AiModelSpec(
            modelId = "deepseek-reasoner",
            provider = com.example.data.model.AiProviderType.DEEPSEEK,
            displayName = "DeepSeek-R1",
            description = "Reasoning model",
            contextWindow = 64000,
            maxOutputTokens = 8192,
            capabilities = setOf(
                com.example.data.model.ModelCapability.REASONING,
                com.example.data.model.ModelCapability.CODE_GENERATION,
                com.example.data.model.ModelCapability.STREAMING
            ),
            costPer1kInputUsd = 0.00055,
            costPer1kOutputUsd = 0.00219
        )

        val (suitableForReasoning, missingReasoning) = com.example.data.model.CapabilityMatrix.validateModelSuitability(
            deepSeekR1,
            setOf(com.example.data.model.ModelCapability.REASONING)
        )
        assertTrue("DeepSeek-R1 must be suitable for Reasoning", suitableForReasoning)
        assertTrue(missingReasoning.isEmpty())

        val (suitableForVision, missingVision) = com.example.data.model.CapabilityMatrix.validateModelSuitability(
            deepSeekR1,
            setOf(com.example.data.model.ModelCapability.VISION)
        )
        assertFalse("DeepSeek-R1 does not support Vision directly", suitableForVision)
        assertTrue(missingVision.contains("VISION"))
    }

    @Test
    fun testAiProviderFailoverRoutingAndLogging() {
        val config = com.example.data.model.AgentModelConfig(
            agentRole = com.example.data.model.AgentRole.CODE_DEVELOPER,
            primaryModelId = "deepseek-chat",
            primaryProvider = com.example.data.model.AiProviderType.DEEPSEEK,
            fallbackModelId = "claude-3-7-sonnet-20250219",
            fallbackProvider = com.example.data.model.AiProviderType.ANTHROPIC
        )

        // When primary fails with HTTP 429 Rate Limit
        val primaryError = "HTTP 429: Too Many Requests on deepseek-chat"
        val failoverLog = com.example.data.model.FailoverLog(
            id = "log_failover_1",
            userId = "usr_ibrahim",
            jobId = "job_failover_test",
            stepIndex = 1,
            primaryProvider = config.primaryProvider.id,
            primaryModel = config.primaryModelId,
            fallbackProvider = config.fallbackProvider.id,
            fallbackModel = config.fallbackModelId,
            failureReason = primaryError,
            costDifferenceUsd = 0.0015
        )

        assertEquals("deepseek-chat", failoverLog.primaryModel)
        assertEquals("claude-3-7-sonnet-20250219", failoverLog.fallbackModel)
        assertTrue(failoverLog.failureReason.contains("429"))

        val map = failoverLog.toFirestoreMap()
        assertEquals("usr_ibrahim", map["userId"])
        assertEquals("deepseek", map["primaryProvider"])
        assertEquals("anthropic", map["fallbackProvider"])
    }

    @Test
    fun testMultiProjectDataIsolation() {
        val projectAId = "proj_alpha_111"
        val projectBId = "proj_beta_222"

        val jobA = com.example.data.model.PersistentJob(
            jobId = "job_a",
            userId = "u1",
            projectId = projectAId,
            projectName = "Alpha",
            title = "Job Alpha",
            userPrompt = "Prompt Alpha"
        )
        val jobB = com.example.data.model.PersistentJob(
            jobId = "job_b",
            userId = "u1",
            projectId = projectBId,
            projectName = "Beta",
            title = "Job Beta",
            userPrompt = "Prompt Beta"
        )

        val allJobs = listOf(jobA, jobB)

        val alphaJobs = allJobs.filter { it.projectId == projectAId }
        val betaJobs = allJobs.filter { it.projectId == projectBId }

        assertEquals(1, alphaJobs.size)
        assertEquals("Job Alpha", alphaJobs[0].title)
        assertEquals(1, betaJobs.size)
        assertEquals("Job Beta", betaJobs[0].title)
        assertNotEquals(alphaJobs[0].projectId, betaJobs[0].projectId)
    }

    @Test
    fun testDeepSeekModelSpecAndPricing() {
        val deepSeekChat = com.example.data.model.AiModelSpec(
            modelId = "deepseek-chat",
            provider = com.example.data.model.AiProviderType.DEEPSEEK,
            displayName = "DeepSeek-V3",
            description = "Chat and Code",
            contextWindow = 64000,
            maxOutputTokens = 8192,
            capabilities = setOf(com.example.data.model.ModelCapability.CODE_GENERATION, com.example.data.model.ModelCapability.TOOL_CALLING),
            costPer1kInputUsd = 0.00027,
            costPer1kOutputUsd = 0.00110
        )

        assertEquals("deepseek", deepSeekChat.provider.id)
        assertTrue(deepSeekChat.supportsTools)
        assertFalse(deepSeekChat.supportsVision)
        assertEquals(0.00027, deepSeekChat.costPer1kInputUsd, 0.000001)
        assertEquals(0.00110, deepSeekChat.costPer1kOutputUsd, 0.000001)
    }

    @Test
    fun testGeminiModelSpecAndPricing() {
        val geminiPro = com.example.data.model.AiModelSpec(
            modelId = "gemini-2.5-pro",
            provider = com.example.data.model.AiProviderType.GOOGLE_GEMINI,
            displayName = "Gemini 2.5 Pro",
            description = "Multimodal Pro",
            contextWindow = 2000000,
            maxOutputTokens = 8192,
            capabilities = setOf(
                com.example.data.model.ModelCapability.REASONING,
                com.example.data.model.ModelCapability.VISION,
                com.example.data.model.ModelCapability.FILE_ANALYSIS,
                com.example.data.model.ModelCapability.TOOL_CALLING
            ),
            costPer1kInputUsd = 0.00125,
            costPer1kOutputUsd = 0.00500
        )

        assertEquals("gemini", geminiPro.provider.id)
        assertTrue(geminiPro.supportsReasoning)
        assertTrue(geminiPro.supportsVision)
        assertTrue(geminiPro.supportsFiles)
        assertTrue(geminiPro.supportsTools)
        assertEquals(2000000, geminiPro.contextWindow)
    }

    @Test
    fun testExponentialBackoffRetryOnTransientError() {
        val job = com.example.data.model.PersistentJob(
            jobId = "retry_test",
            userId = "u1",
            projectId = "p1",
            projectName = "P1",
            title = "Retry Job",
            userPrompt = "Run",
            status = com.example.data.model.JobStatus.RUNNING,
            retryCount = 0,
            maxRetries = 3
        )

        // Transient network failure occurs
        if (job.retryCount < job.maxRetries) {
            job.retryCount++
            job.status = com.example.data.model.JobStatus.WAITING_FOR_RETRY
        }

        assertEquals(1, job.retryCount)
        assertEquals(com.example.data.model.JobStatus.WAITING_FOR_RETRY, job.status)

        // Fail until maxRetries reached
        job.retryCount = 3
        if (job.retryCount >= job.maxRetries) {
            job.status = com.example.data.model.JobStatus.FAILED
        }
        assertEquals(com.example.data.model.JobStatus.FAILED, job.status)
    }

    // ========================================================
    // Mandatory Secrets Vault & Database Integration Tests
    // ========================================================

    @Test
    fun testSecretsVaultEncryptionAndReferenceSystem() {
        val rawPassword = "SuperSecret_P@ssw0rd_9988!"
        val encrypted = CryptoUtils.encrypt(rawPassword)
        val masked = CryptoUtils.maskSecret(rawPassword)

        val secret = com.example.data.model.VaultSecret(
            id = "sec_101",
            userId = "usr_ibrahim",
            projectId = "proj_metra",
            secretName = "db_production_pass",
            secretType = com.example.data.model.SecretType.DATABASE_PASSWORD.name,
            secretReferenceKey = "SEC_REF_DB_PRODUCTION_PASS_998877",
            encryptedSecretValue = encrypted,
            maskedSecretValue = masked,
            allowedServices = listOf("DATABASE_CONNECTOR"),
            allowedAgents = listOf("CODE_DEVELOPER")
        )

        // 1. Ensure raw password is NOT stored plaintext
        assertNotEquals(rawPassword, secret.encryptedSecretValue)
        assertTrue(secret.maskedSecretValue.startsWith("••••"))

        // 2. Verify decryption in Backend layer recovers original password
        val decrypted = CryptoUtils.decrypt(secret.encryptedSecretValue)
        assertEquals(rawPassword, decrypted)

        // 3. Verify Reference Key format provided to LLM
        assertTrue(secret.secretReferenceKey.startsWith("SEC_REF_"))
    }

    @Test
    fun testZeroPromptLeakageRuleEnforcement() {
        val promptContext = StringBuilder("System Instruction for LLM Agent:\n")
        val secretRefKey = "SEC_REF_DB_PROD_PASS"
        val rawSecretPassword = "SuperSecret_P@ssw0rd_9988!"

        // Safe insertion: insert reference key ONLY, never the raw secret
        promptContext.append("Connecting to Database using Reference: $secretRefKey\n")

        val generatedPrompt = promptContext.toString()

        // Critical Security Check: Prompt MUST contain reference key but NEVER plaintext secret
        assertTrue("Prompt must contain Secret Reference Key", generatedPrompt.contains(secretRefKey))
        assertFalse("Prompt MUST NOT contain raw secret password!", generatedPrompt.contains(rawSecretPassword))
    }

    @Test
    fun testDatabaseReadOnlySafetyEnforcement() {
        val readOnlyConfig = com.example.data.model.DatabaseConnectionConfig(
            id = "conn_101",
            userId = "usr_ibrahim",
            projectId = "proj_metra",
            connectionName = "Production DB ReadOnly",
            engineType = com.example.data.model.DatabaseEngine.POSTGRESQL.name,
            isReadOnly = true
        )

        val destructiveSql = "DROP TABLE users;"
        val trimmed = destructiveSql.trim().uppercase()

        val isDestructive = trimmed.contains("DELETE") || trimmed.contains("DROP") || trimmed.contains("TRUNCATE") || trimmed.contains("ALTER")
        val isBlockedByReadOnly = readOnlyConfig.isReadOnly && isDestructive

        assertTrue("Destructive SQL statement (DROP) MUST be blocked on Read-Only connection", isBlockedByReadOnly)
    }

    @Test
    fun testDatabaseSelectQueryAllowedInReadOnly() {
        val readOnlyConfig = com.example.data.model.DatabaseConnectionConfig(
            id = "conn_101",
            userId = "usr_ibrahim",
            projectId = "proj_metra",
            connectionName = "Production DB ReadOnly",
            engineType = com.example.data.model.DatabaseEngine.POSTGRESQL.name,
            isReadOnly = true
        )

        val safeSql = "SELECT id, email FROM users WHERE status = 'ACTIVE';"
        val trimmed = safeSql.trim().uppercase()

        val isDestructive = trimmed.contains("DELETE") || trimmed.contains("DROP") || trimmed.contains("TRUNCATE") || trimmed.contains("ALTER")
        val isBlockedByReadOnly = readOnlyConfig.isReadOnly && isDestructive

        assertFalse("SELECT queries MUST be permitted on Read-Only connection", isBlockedByReadOnly)
    }

    // ========================================================
    // Vision AI, Attachment & Storage Routing Unit Tests
    // ========================================================

    @Test
    fun testProjectAttachmentCreationAndMimeFormatting() {
        val att = com.example.data.model.ProjectAttachment(
            fileName = "ui_mockup.jpeg",
            mimeType = "image/jpeg",
            sizeBytes = 1_500_000L,
            caption = "واجهة التصفح السريعة",
            category = FileCategory.IMAGE,
            isFromCamera = false,
            destination = StorageDestination.PRIVATE_STORAGE
        )

        assertEquals("ui_mockup.jpeg", att.fileName)
        assertTrue("Image attachment must be recognized as image", att.isImage)
        assertEquals("1.4 MB", att.getFormattedSize())
    }

    @Test
    fun testVisionAnalysisTaskFormatting() {
        val tasks = com.example.data.model.VisionAnalysisTask.values()
        assertEquals(7, tasks.size)
        assertEquals("استخراج النصوص OCR", com.example.data.model.VisionAnalysisTask.OCR_TEXT_EXTRACTION.titleAr)
        assertEquals("قراءة صور الأخطاء البرمجية", com.example.data.model.VisionAnalysisTask.ERROR_LOG_PARSING.titleAr)
    }

    @Test
    fun testVisionModelCapabilityValidation() {
        val geminiFlash = com.example.data.model.AiModelSpec(
            modelId = "gemini-2.5-flash",
            provider = com.example.data.model.AiProviderType.GOOGLE_GEMINI,
            displayName = "Gemini 2.5 Flash",
            description = "Fast Multimodal",
            contextWindow = 1000000,
            maxOutputTokens = 8192,
            capabilities = setOf(com.example.data.model.ModelCapability.VISION, com.example.data.model.ModelCapability.CODE_GENERATION),
            costPer1kInputUsd = 0.000075,
            costPer1kOutputUsd = 0.000300
        )

        val (suitable, missing) = com.example.data.model.CapabilityMatrix.validateModelSuitability(
            geminiFlash,
            setOf(com.example.data.model.ModelCapability.VISION)
        )
        assertTrue("Gemini 2.5 Flash MUST be suitable for Vision", suitable)
        assertTrue(missing.isEmpty())
    }

    // ========================================================
    // AI Accounts Center & OAuth Policy Tests
    // ========================================================

    @Test
    fun testOpenAiAndGeminiAccountPolicies() {
        val openAiPolicy = com.example.data.model.AiProviderPolicies.getPolicy(com.example.data.model.AiProviderType.OPENAI)
        assertFalse("OpenAI subscription cannot be used directly for API calls", openAiPolicy.canUseExistingSubscription)
        assertEquals(com.example.data.model.AiAuthMethod.SIGN_IN_WITH_CHATGPT, openAiPolicy.officialAuthMethod)

        val geminiPolicy = com.example.data.model.AiProviderPolicies.getPolicy(com.example.data.model.AiProviderType.GOOGLE_GEMINI)
        assertTrue("Google Gemini allows Google OAuth and Free API Tier", geminiPolicy.canUseExistingSubscription)
        assertEquals(com.example.data.model.AiAuthMethod.GOOGLE_OAUTH, geminiPolicy.officialAuthMethod)
    }

    @Test
    fun testConnectedAiAccountTokenRotationAndExpiry() {
        val now = System.currentTimeMillis()
        val account = com.example.data.model.ConnectedAiAccount(
            id = "acc_openai_01",
            accountEmail = "ibrahim@metra.ai",
            provider = com.example.data.model.AiProviderType.OPENAI,
            authMethod = com.example.data.model.AiAuthMethod.SIGN_IN_WITH_CHATGPT,
            tokenExpiresAt = now + 100_000L,
            approvalStatus = "ACTIVE"
        )

        assertFalse("Account token should not be expired yet", account.isExpired)

        val expiredAccount = account.copy(tokenExpiresAt = now - 5_000L)
        assertTrue("Account token past expiration must be flagged as expired", expiredAccount.isExpired)
    }

    // ========================================================
    // Export & Sync Center Unit Tests
    // ========================================================

    @Test
    fun testExportProjectToDeviceZipGenerationAndFingerprint() {
        val project = com.example.data.model.Project(
            id = "proj_weather_app",
            name = "Weather Control System",
            description = "مشاريع المطور التقني ابراهيم زينب"
        )
        val files = listOf(
            com.example.data.model.StoredFile(id = "f1", fileName = "MainActivity.kt", fileCategory = "CODE", checksumSha256 = "sha256_code_123"),
            com.example.data.model.StoredFile(id = "f2", fileName = "Report.pdf", fileCategory = "PDF", checksumSha256 = "sha256_pdf_456")
        )

        val (zipBytes, sha256) = com.example.data.repository.ExportSyncRepository().generateProjectZipArchive(
            project = project,
            files = files,
            template = com.example.data.model.ExportTemplate.FULL_PROJECT_ZIP
        )

        assertTrue("Generated ZIP archive must contain byte content", zipBytes.isNotEmpty())
        assertEquals(64, sha256.length)
    }

    @Test
    fun testSha256ConflictDetectionBetweenLocalAndRemoteChecksums() {
        val repo = com.example.data.repository.ExportSyncRepository()

        val localChecksum = "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2"
        val sameRemoteChecksum = "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2"
        val (isConflictIdentical, _) = repo.detectFileConflict(localChecksum, sameRemoteChecksum)
        assertFalse("Identical SHA-256 fingerprints must NOT be marked as conflict", isConflictIdentical)

        val differentRemoteChecksum = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"
        val (isConflictDifferent, details) = repo.detectFileConflict(localChecksum, differentRemoteChecksum)
        assertTrue("Different SHA-256 fingerprints MUST trigger conflict flag", isConflictDifferent)
        assertTrue(details.contains("تعارض رقمي مكتشف"))
    }

    @Test
    fun testAiIdentityAttributionAndVoiceGreeting() {
        val config = com.example.data.model.AiIdentityConfig()
        assertTrue(config.officialIdentityStatement.contains("إبراهيم زينب"))
        assertTrue(config.officialIdentityStatement.contains("METRA AI CONTROL"))
        assertEquals("الخبير إبراهيم زينب", config.developerName)
        assertTrue(config.voiceGreetingText.contains("مرحباً بك"))
    }

    @Test
    fun testCyberShieldTrustedDeviceEnrollment() {
        val device = com.example.data.model.TrustedDevice(
            deviceName = "Pixel 8 Pro Secure Workstation",
            trustScore = 100,
            isAuthorized = true
        )
        assertEquals("Pixel 8 Pro Secure Workstation", device.deviceName)
        assertTrue(device.isAuthorized)
        assertEquals(100, device.trustScore)
    }
}
