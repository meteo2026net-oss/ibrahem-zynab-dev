package com.example.data.remote

import android.util.Log
import com.example.data.model.AiGenerationRequest
import com.example.data.model.AiGenerationResponse
import com.example.data.model.AiModelSpec
import com.example.data.model.AiProviderType
import com.example.data.model.FailoverLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

interface AiProviderClient {
    val providerType: AiProviderType
    suspend fun testConnection(apiKey: String, modelId: String): Pair<Boolean, String>
    suspend fun generate(apiKey: String, request: AiGenerationRequest): AiGenerationResponse
}

class DeepSeekClient(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
) : AiProviderClient {
    override val providerType: AiProviderType = AiProviderType.DEEPSEEK

    override suspend fun testConnection(apiKey: String, modelId: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Pair(false, "DeepSeek API Key مطلوب")
        try {
            val jsonBody = JSONObject().apply {
                put("model", if (modelId.isNotBlank()) modelId else "deepseek-chat")
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", "ping")
                    })
                })
                put("max_tokens", 5)
            }

            val request = Request.Builder()
                .url("https://api.deepseek.com/chat/completions")
                .header("Authorization", "Bearer $apiKey")
                .header("Content-Type", "application/json")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Pair(true, "تم الاتصال بنجاح بـ DeepSeek API (HTTP ${response.code})")
                } else {
                    val errorMsg = try {
                        JSONObject(body).optJSONObject("error")?.optString("message") ?: body
                    } catch (e: Exception) {
                        body
                    }
                    Pair(false, "فشل الاتصال بـ DeepSeek (HTTP ${response.code}): $errorMsg")
                }
            }
        } catch (e: Exception) {
            Pair(false, "خطأ في الشبكة أو الاتصال بـ DeepSeek: ${e.localizedMessage}")
        }
    }

    override suspend fun generate(apiKey: String, request: AiGenerationRequest): AiGenerationResponse = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val jsonBody = JSONObject().apply {
            put("model", request.modelId)
            put("messages", JSONArray().apply {
                if (request.systemPrompt.isNotBlank()) {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", request.systemPrompt)
                    })
                }
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", request.userPrompt)
                })
            })
            put("temperature", request.temperature)
            put("max_tokens", request.maxTokens)
        }

        val httpRequest = Request.Builder()
            .url("https://api.deepseek.com/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(httpRequest).execute().use { response ->
            val body = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                val errorMsg = try {
                    JSONObject(body).optJSONObject("error")?.optString("message") ?: "HTTP ${response.code}"
                } catch (e: Exception) {
                    "HTTP ${response.code}: $body"
                }
                throw IOException("DeepSeek Error: $errorMsg")
            }

            val json = JSONObject(body)
            val choices = json.optJSONArray("choices")
            val firstChoice = choices?.optJSONObject(0)
            val message = firstChoice?.optJSONObject("message")
            val content = message?.optString("content") ?: ""
            val reasoning = message?.optString("reasoning_content")

            val usage = json.optJSONObject("usage")
            val promptTokens = usage?.optInt("prompt_tokens", 0) ?: 0
            val completionTokens = usage?.optInt("completion_tokens", 0) ?: 0

            val costPer1kInput = if (request.modelId.contains("reasoner")) 0.00055 else 0.00027
            val costPer1kOutput = if (request.modelId.contains("reasoner")) 0.00219 else 0.00110
            val estimatedCost = (promptTokens * costPer1kInput / 1000.0) + (completionTokens * costPer1kOutput / 1000.0)

            AiGenerationResponse(
                content = content,
                reasoningTrace = reasoning,
                inputTokens = promptTokens,
                outputTokens = completionTokens,
                estimatedCostUsd = estimatedCost,
                modelUsed = request.modelId,
                providerUsed = AiProviderType.DEEPSEEK,
                durationMs = System.currentTimeMillis() - startTime,
                rawResponse = body
            )
        }
    }
}

class GeminiProviderClient(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
) : AiProviderClient {
    override val providerType: AiProviderType = AiProviderType.GOOGLE_GEMINI

    override suspend fun testConnection(apiKey: String, modelId: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Pair(false, "Google Gemini API Key مطلوب")
        try {
            val targetModel = if (modelId.isNotBlank()) modelId else "gemini-2.5-flash"
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", "ping") })
                        })
                    })
                })
            }

            val url = "https://generativelanguage.googleapis.com/v1beta/models/$targetModel:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Pair(true, "تم الاتصال بنجاح بـ Gemini API ($targetModel) - HTTP 200")
                } else {
                    val errorMsg = try {
                        JSONObject(body).optJSONObject("error")?.optString("message") ?: body
                    } catch (e: Exception) {
                        body
                    }
                    Pair(false, "فشل الاتصال بـ Gemini (HTTP ${response.code}): $errorMsg")
                }
            }
        } catch (e: Exception) {
            Pair(false, "خطأ في الاتصال بـ Gemini: ${e.localizedMessage}")
        }
    }

    override suspend fun generate(apiKey: String, request: AiGenerationRequest): AiGenerationResponse = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val targetModel = if (request.modelId.isNotBlank()) request.modelId else "gemini-2.5-flash"
        val jsonBody = JSONObject().apply {
            if (request.systemPrompt.isNotBlank()) {
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", request.systemPrompt) })
                    })
                })
            }
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", request.userPrompt) })
                        request.imageAttachments.forEach { att ->
                            if (!att.base64Data.isNullOrBlank()) {
                                put(JSONObject().apply {
                                    put("inlineData", JSONObject().apply {
                                        put("mimeType", att.mimeType.ifBlank { "image/jpeg" })
                                        put("data", att.base64Data)
                                    })
                                })
                            }
                        }
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("temperature", request.temperature)
                put("maxOutputTokens", request.maxTokens)
            })
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$targetModel:generateContent?key=$apiKey"
        val httpRequest = Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(httpRequest).execute().use { response ->
            val body = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                val errorMsg = try {
                    JSONObject(body).optJSONObject("error")?.optString("message") ?: "HTTP ${response.code}"
                } catch (e: Exception) {
                    "HTTP ${response.code}: $body"
                }
                throw IOException("Gemini Error: $errorMsg")
            }

            val json = JSONObject(body)
            val candidates = json.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val contentObj = firstCandidate?.optJSONObject("content")
            val parts = contentObj?.optJSONArray("parts")
            val textBuilder = StringBuilder()
            if (parts != null) {
                for (i in 0 until parts.length()) {
                    val part = parts.optJSONObject(i)
                    textBuilder.append(part?.optString("text") ?: "")
                }
            }

            val usage = json.optJSONObject("usageMetadata")
            val promptTokens = usage?.optInt("promptTokenCount", 0) ?: 0
            val completionTokens = usage?.optInt("candidatesTokenCount", 0) ?: 0

            val costPer1kInput = if (targetModel.contains("pro")) 0.00125 else 0.000075
            val costPer1kOutput = if (targetModel.contains("pro")) 0.00500 else 0.000300
            val estimatedCost = (promptTokens * costPer1kInput / 1000.0) + (completionTokens * costPer1kOutput / 1000.0)

            AiGenerationResponse(
                content = textBuilder.toString(),
                inputTokens = promptTokens,
                outputTokens = completionTokens,
                estimatedCostUsd = estimatedCost,
                modelUsed = targetModel,
                providerUsed = AiProviderType.GOOGLE_GEMINI,
                durationMs = System.currentTimeMillis() - startTime,
                rawResponse = body
            )
        }
    }
}

class OpenAiClient(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
) : AiProviderClient {
    override val providerType: AiProviderType = AiProviderType.OPENAI

    override suspend fun testConnection(apiKey: String, modelId: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Pair(false, "OpenAI API Key مطلوب")
        try {
            val targetModel = if (modelId.isNotBlank()) modelId else "gpt-4o-mini"
            val jsonBody = JSONObject().apply {
                put("model", targetModel)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", "ping")
                    })
                })
                put("max_tokens", 5)
            }

            val request = Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .header("Authorization", "Bearer $apiKey")
                .header("Content-Type", "application/json")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Pair(true, "تم الاتصال بنجاح بـ OpenAI ($targetModel) - HTTP 200")
                } else {
                    val errorMsg = try {
                        JSONObject(body).optJSONObject("error")?.optString("message") ?: body
                    } catch (e: Exception) {
                        body
                    }
                    Pair(false, "فشل الاتصال بـ OpenAI (HTTP ${response.code}): $errorMsg")
                }
            }
        } catch (e: Exception) {
            Pair(false, "خطأ في الاتصال بـ OpenAI: ${e.localizedMessage}")
        }
    }

    override suspend fun generate(apiKey: String, request: AiGenerationRequest): AiGenerationResponse = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val jsonBody = JSONObject().apply {
            put("model", request.modelId)
            put("messages", JSONArray().apply {
                if (request.systemPrompt.isNotBlank()) {
                    put(JSONObject().apply {
                        put("role", "system")
                        put("content", request.systemPrompt)
                    })
                }
                val userContent: Any = if (request.imageAttachments.isNotEmpty()) {
                    JSONArray().apply {
                        put(JSONObject().apply {
                            put("type", "text")
                            put("text", request.userPrompt)
                        })
                        request.imageAttachments.forEach { att ->
                            if (!att.base64Data.isNullOrBlank()) {
                                put(JSONObject().apply {
                                    put("type", "image_url")
                                    put("image_url", JSONObject().apply {
                                        put("url", "data:${att.mimeType.ifBlank { "image/jpeg" }};base64,${att.base64Data}")
                                    })
                                })
                            }
                        }
                    }
                } else {
                    request.userPrompt
                }
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", userContent)
                })
            })
            if (!request.modelId.startsWith("o1") && !request.modelId.startsWith("o3")) {
                put("temperature", request.temperature)
            }
            put("max_tokens", request.maxTokens)
        }

        val httpRequest = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(httpRequest).execute().use { response ->
            val body = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                val errorMsg = try {
                    JSONObject(body).optJSONObject("error")?.optString("message") ?: "HTTP ${response.code}"
                } catch (e: Exception) {
                    "HTTP ${response.code}: $body"
                }
                throw IOException("OpenAI Error: $errorMsg")
            }

            val json = JSONObject(body)
            val choices = json.optJSONArray("choices")
            val firstChoice = choices?.optJSONObject(0)
            val message = firstChoice?.optJSONObject("message")
            val content = message?.optString("content") ?: ""

            val usage = json.optJSONObject("usage")
            val promptTokens = usage?.optInt("prompt_tokens", 0) ?: 0
            val completionTokens = usage?.optInt("completion_tokens", 0) ?: 0

            val costPer1kInput = if (request.modelId.contains("mini")) 0.00015 else 0.00250
            val costPer1kOutput = if (request.modelId.contains("mini")) 0.00060 else 0.01000
            val estimatedCost = (promptTokens * costPer1kInput / 1000.0) + (completionTokens * costPer1kOutput / 1000.0)

            AiGenerationResponse(
                content = content,
                inputTokens = promptTokens,
                outputTokens = completionTokens,
                estimatedCostUsd = estimatedCost,
                modelUsed = request.modelId,
                providerUsed = AiProviderType.OPENAI,
                durationMs = System.currentTimeMillis() - startTime,
                rawResponse = body
            )
        }
    }
}

class AnthropicClient(
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
) : AiProviderClient {
    override val providerType: AiProviderType = AiProviderType.ANTHROPIC

    override suspend fun testConnection(apiKey: String, modelId: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) return@withContext Pair(false, "Anthropic API Key مطلوب")
        try {
            val targetModel = if (modelId.isNotBlank()) modelId else "claude-3-5-haiku-20241022"
            val jsonBody = JSONObject().apply {
                put("model", targetModel)
                put("messages", JSONArray().apply {
                    put(JSONObject().apply {
                        put("role", "user")
                        put("content", "ping")
                    })
                })
                put("max_tokens", 5)
            }

            val request = Request.Builder()
                .url("https://api.anthropic.com/v1/messages")
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .header("Content-Type", "application/json")
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                val body = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Pair(true, "تم الاتصال بنجاح بـ Anthropic ($targetModel) - HTTP 200")
                } else {
                    val errorMsg = try {
                        JSONObject(body).optJSONObject("error")?.optString("message") ?: body
                    } catch (e: Exception) {
                        body
                    }
                    Pair(false, "فشل الاتصال بـ Anthropic (HTTP ${response.code}): $errorMsg")
                }
            }
        } catch (e: Exception) {
            Pair(false, "خطأ في الاتصال بـ Anthropic: ${e.localizedMessage}")
        }
    }

    override suspend fun generate(apiKey: String, request: AiGenerationRequest): AiGenerationResponse = withContext(Dispatchers.IO) {
        val startTime = System.currentTimeMillis()
        val jsonBody = JSONObject().apply {
            put("model", request.modelId)
            if (request.systemPrompt.isNotBlank()) {
                put("system", request.systemPrompt)
            }
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", request.userPrompt)
                })
            })
            put("max_tokens", request.maxTokens)
        }

        val httpRequest = Request.Builder()
            .url("https://api.anthropic.com/v1/messages")
            .header("x-api-key", apiKey)
            .header("anthropic-version", "2023-06-01")
            .header("Content-Type", "application/json")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(httpRequest).execute().use { response ->
            val body = response.body?.string() ?: ""
            if (!response.isSuccessful) {
                val errorMsg = try {
                    JSONObject(body).optJSONObject("error")?.optString("message") ?: "HTTP ${response.code}"
                } catch (e: Exception) {
                    "HTTP ${response.code}: $body"
                }
                throw IOException("Anthropic Error: $errorMsg")
            }

            val json = JSONObject(body)
            val contentArr = json.optJSONArray("content")
            val textBuilder = StringBuilder()
            if (contentArr != null) {
                for (i in 0 until contentArr.length()) {
                    val item = contentArr.optJSONObject(i)
                    if (item?.optString("type") == "text") {
                        textBuilder.append(item.optString("text"))
                    }
                }
            }

            val usage = json.optJSONObject("usage")
            val promptTokens = usage?.optInt("input_tokens", 0) ?: 0
            val completionTokens = usage?.optInt("output_tokens", 0) ?: 0

            val costPer1kInput = if (request.modelId.contains("haiku")) 0.00080 else 0.00300
            val costPer1kOutput = if (request.modelId.contains("haiku")) 0.00400 else 0.01500
            val estimatedCost = (promptTokens * costPer1kInput / 1000.0) + (completionTokens * costPer1kOutput / 1000.0)

            AiGenerationResponse(
                content = textBuilder.toString(),
                inputTokens = promptTokens,
                outputTokens = completionTokens,
                estimatedCostUsd = estimatedCost,
                modelUsed = request.modelId,
                providerUsed = AiProviderType.ANTHROPIC,
                durationMs = System.currentTimeMillis() - startTime,
                rawResponse = body
            )
        }
    }
}
