package com.example.data.model

enum class AiProviderType(
    val id: String,
    val displayName: String,
    val defaultEndpoint: String,
    val documentationUrl: String
) {
    DEEPSEEK("deepseek", "DeepSeek", "https://api.deepseek.com", "https://api-docs.deepseek.com"),
    GOOGLE_GEMINI("gemini", "Google Gemini", "https://generativelanguage.googleapis.com/v1beta", "https://ai.google.dev"),
    OPENAI("openai", "OpenAI", "https://api.openai.com/v1", "https://platform.openai.com/docs"),
    ANTHROPIC("anthropic", "Anthropic Claude", "https://api.anthropic.com/v1", "https://docs.anthropic.com")
}

enum class ModelCapability {
    REASONING,
    CODE_GENERATION,
    VISION,
    FILE_ANALYSIS,
    TOOL_CALLING,
    STREAMING,
    LARGE_CONTEXT
}

data class AiModelSpec(
    val modelId: String,
    val provider: AiProviderType,
    val displayName: String,
    val description: String,
    val contextWindow: Int,
    val maxOutputTokens: Int,
    val capabilities: Set<ModelCapability>,
    val costPer1kInputUsd: Double,
    val costPer1kOutputUsd: Double,
    val isAvailable: Boolean = true,
    val lastVerifiedAt: Long = System.currentTimeMillis()
) {
    val supportsVision: Boolean get() = capabilities.contains(ModelCapability.VISION)
    val supportsFiles: Boolean get() = capabilities.contains(ModelCapability.FILE_ANALYSIS)
    val supportsTools: Boolean get() = capabilities.contains(ModelCapability.TOOL_CALLING)
    val supportsReasoning: Boolean get() = capabilities.contains(ModelCapability.REASONING)
    val supportsStreaming: Boolean get() = capabilities.contains(ModelCapability.STREAMING)

    fun toFirestoreMap(userId: String): Map<String, Any?> = mapOf(
        "userId" to userId,
        "provider" to provider.id,
        "modelId" to modelId,
        "displayName" to displayName,
        "contextWindow" to contextWindow,
        "maxOutputTokens" to maxOutputTokens,
        "supportsVision" to supportsVision,
        "supportsFiles" to supportsFiles,
        "supportsTools" to supportsTools,
        "supportsReasoning" to supportsReasoning,
        "supportsStreaming" to supportsStreaming,
        "costPer1kInputTokens" to costPer1kInputUsd,
        "costPer1kOutputTokens" to costPer1kOutputUsd,
        "isAvailable" to isAvailable,
        "lastVerifiedAt" to lastVerifiedAt
    )
}

object CapabilityMatrix {
    fun validateModelSuitability(
        model: AiModelSpec,
        requiredCapabilities: Set<ModelCapability>
    ): Pair<Boolean, List<String>> {
        val missing = requiredCapabilities.filter { !model.capabilities.contains(it) }
        val missingNames = missing.map { it.name }
        return Pair(missing.isEmpty(), missingNames)
    }
}

enum class AgentRole(val id: String, val titleAr: String, val primaryCapability: ModelCapability) {
    REQUIREMENTS_ANALYSIS("requirements", "تحليل المتطلبات والاستدلال", ModelCapability.REASONING),
    CODE_DEVELOPER("developer", "تطوير وكتابة الكود", ModelCapability.CODE_GENERATION),
    SECURITY_AUDITOR("security", "المراجعة والفحص الأمني", ModelCapability.CODE_GENERATION),
    DOCUMENTATION("documentation", "التوثيق والشرح", ModelCapability.LARGE_CONTEXT),
    TESTING_QA("testing", "هندسة وتحليل الاختبارات", ModelCapability.TOOL_CALLING)
}

data class AgentModelConfig(
    val agentRole: AgentRole,
    val primaryModelId: String,
    val primaryProvider: AiProviderType,
    val fallbackModelId: String,
    val fallbackProvider: AiProviderType
)

data class FailoverLog(
    val id: String,
    val userId: String,
    val jobId: String,
    val stepIndex: Int,
    val primaryProvider: String,
    val primaryModel: String,
    val fallbackProvider: String,
    val fallbackModel: String,
    val failureReason: String,
    val costDifferenceUsd: Double,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "jobId" to jobId,
        "stepIndex" to stepIndex,
        "primaryProvider" to primaryProvider,
        "primaryModel" to primaryModel,
        "fallbackProvider" to fallbackProvider,
        "fallbackModel" to fallbackModel,
        "failureReason" to failureReason,
        "costDifference" to costDifferenceUsd,
        "timestamp" to timestamp
    )
}

data class AiGenerationRequest(
    val systemPrompt: String,
    val userPrompt: String,
    val modelId: String,
    val temperature: Double = 0.2,
    val maxTokens: Int = 4096,
    val requiredCapabilities: Set<ModelCapability> = emptySet(),
    val tools: List<String> = emptyList(),
    val imageAttachments: List<ProjectAttachment> = emptyList()
)

data class AiGenerationResponse(
    val content: String,
    val reasoningTrace: String? = null,
    val inputTokens: Int = 0,
    val outputTokens: Int = 0,
    val estimatedCostUsd: Double = 0.0,
    val modelUsed: String,
    val providerUsed: AiProviderType,
    val isFailover: Boolean = false,
    val durationMs: Long = 0,
    val rawResponse: String? = null
)
