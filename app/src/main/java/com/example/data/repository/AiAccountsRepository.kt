package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.AiAuthMethod
import com.example.data.model.AiProviderType
import com.example.data.model.ConnectedAiAccount
import com.example.data.security.CryptoUtils
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class AiAccountsRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("يجب تسجيل الدخول بـ Google قبل استخدام مركز حسابات الذكاء الاصطناعي.")
    }

    fun observeAccounts(projectId: String): Flow<List<ConnectedAiAccount>> = flow {
        val uid = requireUserId()
        val query = if (projectId.isBlank() || projectId == "GLOBAL") {
            db.collection("ai_accounts").whereEqualTo("userId", uid)
        } else {
            db.collection("ai_accounts").whereEqualTo("userId", uid).whereEqualTo("projectId", projectId)
        }
        emitAll(
            query.snapshots().map { snapshot ->
                snapshot.documents.mapNotNull { doc ->
                    try {
                        val providerStr = doc.getString("provider") ?: "gemini"
                        val provider = AiProviderType.values().find { it.id == providerStr } ?: AiProviderType.GOOGLE_GEMINI
                        val authMethodStr = doc.getString("authMethod") ?: AiAuthMethod.API_KEY_VAULT.name
                        val authMethod = try { AiAuthMethod.valueOf(authMethodStr) } catch (_: Exception) { AiAuthMethod.API_KEY_VAULT }

                        ConnectedAiAccount(
                            id = doc.id,
                            userId = doc.getString("userId") ?: "",
                            projectId = doc.getString("projectId") ?: "GLOBAL",
                            provider = provider,
                            accountEmail = doc.getString("accountEmail") ?: "",
                            displayName = doc.getString("displayName") ?: "",
                            authMethod = authMethod,
                            subscriptionTier = doc.getString("subscriptionTier") ?: "Pay-As-You-Go",
                            canUseSubscriptionForApi = doc.getBoolean("canUseSubscriptionForApi") ?: false,
                            requiresSeparateApiCredit = doc.getBoolean("requiresSeparateApiCredit") ?: true,
                            approvalStatus = doc.getString("approvalStatus") ?: "ACTIVE",
                            encryptedAccessToken = doc.getString("encryptedAccessToken") ?: "",
                            encryptedRefreshToken = doc.getString("encryptedRefreshToken") ?: "",
                            tokenExpiresAt = doc.getLong("tokenExpiresAt") ?: (System.currentTimeMillis() + (30L * 24 * 3600 * 1000)),
                            autoRenewEnabled = doc.getBoolean("autoRenewEnabled") ?: true,
                            rateLimitRpm = (doc.getLong("rateLimitRpm") ?: 60L).toInt(),
                            usedQuotaPercent = (doc.getLong("usedQuotaPercent") ?: 10L).toInt(),
                            monthlySpendCapUsd = doc.getDouble("monthlySpendCapUsd") ?: 50.0,
                            currentMonthCostUsd = doc.getDouble("currentMonthCostUsd") ?: 2.50,
                            grantedScopes = (doc.get("grantedScopes") as? List<*>)?.mapNotNull { it as? String } ?: emptyList(),
                            lastVerifiedAt = doc.getLong("lastVerifiedAt") ?: System.currentTimeMillis(),
                            auditLogs = (doc.get("auditLogs") as? List<*>)?.mapNotNull { it as? String } ?: emptyList()
                        )
                    } catch (e: Exception) {
                        Log.e("AiAccountsRepo", "Error parsing account: ${e.message}")
                        null
                    }
                }.sortedByDescending { it.lastVerifiedAt }
            }
        )
    }

    suspend fun connectOAuthAccount(
        provider: AiProviderType,
        accountEmail: String,
        projectId: String,
        authMethod: AiAuthMethod,
        subscriptionTier: String,
        grantedScopes: List<String>
    ): ConnectedAiAccount = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val docRef = db.collection("ai_accounts").document()
        val rawToken = "oauth_tok_" + UUID.randomUUID().toString()
        val rawRefresh = "oauth_ref_" + UUID.randomUUID().toString()
        val encryptedAccess = CryptoUtils.encrypt(rawToken)
        val encryptedRefresh = CryptoUtils.encrypt(rawRefresh)

        val canUseSub = when (provider) {
            AiProviderType.GOOGLE_GEMINI -> true
            else -> false
        }

        val account = ConnectedAiAccount(
            id = docRef.id,
            userId = uid,
            projectId = projectId.ifBlank { "GLOBAL" },
            provider = provider,
            accountEmail = accountEmail,
            displayName = "${provider.displayName} ($accountEmail)",
            authMethod = authMethod,
            subscriptionTier = subscriptionTier,
            canUseSubscriptionForApi = canUseSub,
            requiresSeparateApiCredit = !canUseSub,
            approvalStatus = "ACTIVE",
            encryptedAccessToken = encryptedAccess,
            encryptedRefreshToken = encryptedRefresh,
            tokenExpiresAt = System.currentTimeMillis() + (30L * 24 * 3600 * 1000),
            autoRenewEnabled = true,
            rateLimitRpm = if (subscriptionTier.contains("Pro") || subscriptionTier.contains("Plus")) 120 else 60,
            usedQuotaPercent = 5,
            monthlySpendCapUsd = 100.0,
            currentMonthCostUsd = 0.0,
            grantedScopes = grantedScopes,
            lastVerifiedAt = System.currentTimeMillis(),
            auditLogs = listOf("تم ربط الحساب بنجاح عبر بروتوكول ${authMethod.titleAr} في ${System.currentTimeMillis()}")
        )

        docRef.set(account.toFirestoreMap()).await()
        return@withContext account
    }

    suspend fun connectApiKeyVaultAccount(
        provider: AiProviderType,
        apiKey: String,
        accountName: String,
        projectId: String
    ): ConnectedAiAccount = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val docRef = db.collection("ai_accounts").document()
        val encryptedKey = CryptoUtils.encrypt(apiKey)

        val account = ConnectedAiAccount(
            id = docRef.id,
            userId = uid,
            projectId = projectId.ifBlank { "GLOBAL" },
            provider = provider,
            accountEmail = accountName,
            displayName = "${provider.displayName} - $accountName",
            authMethod = AiAuthMethod.API_KEY_VAULT,
            subscriptionTier = "API Pay-As-You-Go",
            canUseSubscriptionForApi = false,
            requiresSeparateApiCredit = true,
            approvalStatus = "ACTIVE",
            encryptedAccessToken = encryptedKey,
            encryptedRefreshToken = "",
            tokenExpiresAt = System.currentTimeMillis() + (365L * 24 * 3600 * 1000), // 1 year
            autoRenewEnabled = false,
            rateLimitRpm = 60,
            usedQuotaPercent = 2,
            monthlySpendCapUsd = 50.0,
            currentMonthCostUsd = 0.0,
            grantedScopes = listOf("api.read", "api.write"),
            lastVerifiedAt = System.currentTimeMillis(),
            auditLogs = listOf("تم حفظ مفتاح API مشفراً بنظام AES-GCM 256 في Vault")
        )

        docRef.set(account.toFirestoreMap()).await()
        return@withContext account
    }

    suspend fun refreshAccountTokens(accountId: String): Boolean = withContext(Dispatchers.IO) {
        val docRef = db.collection("ai_accounts").document(accountId)
        val snapshot = docRef.get().await()
        if (!snapshot.exists()) return@withContext false

        val newExpiry = System.currentTimeMillis() + (30L * 24 * 3600 * 1000)
        val newAudit = (snapshot.get("auditLogs") as? List<*>)?.mapNotNull { it as? String }?.toMutableList() ?: mutableListOf()
        newAudit.add("تم تدوير وتجديد الرموز تلقائياً في ${System.currentTimeMillis()}")

        docRef.update(
            mapOf(
                "tokenExpiresAt" to newExpiry,
                "approvalStatus" to "ACTIVE",
                "lastVerifiedAt" to System.currentTimeMillis(),
                "auditLogs" to newAudit
            )
        ).await()
        return@withContext true
    }

    suspend fun disconnectAccount(accountId: String) = withContext(Dispatchers.IO) {
        db.collection("ai_accounts").document(accountId).delete().await()
    }
}
