package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.model.SecretType
import com.example.data.model.VaultSecret
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * Client facade for the server-backed Secrets Vault.
 * Secret encryption/decryption and Firestore writes happen only in trusted Functions.
 */
class SecretsVaultRepository(
    @Suppress("unused") private val context: Context,
    @Suppress("unused") private val integrationsRepository: IntegrationsRepository
) {
    private val functions by lazy { FirebaseFunctions.getInstance() }

    suspend fun addSecret(
        projectId: String,
        secretName: String,
        secretType: SecretType,
        plainSecretValue: String,
        description: String,
        allowedServices: List<String> = listOf("DATABASE_CONNECTOR", "GITHUB_SERVICE", "BACKEND_WORKER"),
        allowedAgents: List<String> = listOf("CODE_DEVELOPER", "TESTING_QA"),
        allowedOperations: List<String> = listOf("READ_SCHEMA", "EXECUTE_QUERY", "CONNECT"),
        expiresAt: Long? = null
    ): VaultSecret = withContext(Dispatchers.IO) {
        val uid = Firebase.auth.currentUser?.uid
            ?: throw IllegalStateException("Must be signed in to store a secret.")
        require(secretName.isNotBlank()) { "Secret name is required." }
        require(plainSecretValue.isNotBlank()) { "Secret value is required." }

        val payload = mutableMapOf<String, Any>(
            "projectId" to projectId,
            "secretName" to secretName.trim(),
            "secretType" to secretType.name,
            "secretValue" to plainSecretValue,
            "description" to description,
            "allowedServices" to allowedServices,
            "allowedAgents" to allowedAgents,
            "allowedOperations" to allowedOperations
        )
        expiresAt?.let { payload["expiresAt"] = it }

        val result = functions.getHttpsCallable("putVaultSecret").call(payload).await()
        val response = result.data as? Map<*, *>
            ?: throw IllegalStateException("Invalid Secrets Vault response.")
        val id = response["id"] as? String
            ?: throw IllegalStateException("Secrets Vault did not return a secret ID.")
        val referenceKey = response["secretReferenceKey"] as? String ?: ""
        val masked = response["maskedSecretValue"] as? String ?: "••••••••"

        VaultSecret(
            id = id,
            userId = uid,
            projectId = projectId,
            secretName = secretName.trim(),
            secretType = secretType.name,
            description = description,
            secretReferenceKey = referenceKey,
            encryptedSecretValue = "",
            maskedSecretValue = masked,
            allowedServices = allowedServices,
            allowedAgents = allowedAgents,
            allowedOperations = allowedOperations,
            expiresAt = expiresAt
        )
    }

    suspend fun deleteSecret(secretId: String, projectId: String) = withContext(Dispatchers.IO) {
        Firebase.auth.currentUser ?: throw IllegalStateException("Must be signed in.")
        functions.getHttpsCallable("deleteVaultSecret")
            .call(mapOf("secretId" to secretId, "projectId" to projectId))
            .await()
        Unit
    }

    /**
     * Secret plaintext is intentionally never returned to the Android client.
     * Trusted server workers use the private IAM-protected resolver endpoint.
     */
    @Suppress("UNUSED_PARAMETER")
    suspend fun resolveSecretBackendOnly(
        referenceKey: String,
        requestingService: String,
        requestingAgent: String
    ): String? = null

    fun observeSecrets(projectId: String): Flow<List<VaultSecret>> = flow {
        val uid = Firebase.auth.currentUser?.uid
        if (uid == null || projectId.isBlank()) {
            emit(emptyList())
            return@flow
        }
        try {
            val result = functions.getHttpsCallable("listVaultSecrets")
                .call(mapOf("projectId" to projectId))
                .await()
            val response = result.data as? Map<*, *>
            val items = response?.get("secrets") as? List<*> ?: emptyList<Any>()
            val secrets = items.mapNotNull { item ->
                val data = item as? Map<*, *> ?: return@mapNotNull null
                val id = data["id"] as? String ?: return@mapNotNull null
                VaultSecret(
                    id = id,
                    userId = uid,
                    projectId = data["projectId"] as? String ?: projectId,
                    secretName = data["secretName"] as? String ?: "",
                    secretType = data["secretType"] as? String ?: SecretType.OTHER_SECRET.name,
                    description = data["description"] as? String ?: "",
                    secretReferenceKey = data["secretReferenceKey"] as? String ?: "",
                    encryptedSecretValue = "",
                    maskedSecretValue = data["maskedSecretValue"] as? String ?: "••••••••",
                    allowedServices = (data["allowedServices"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                    allowedAgents = (data["allowedAgents"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                    allowedOperations = (data["allowedOperations"] as? List<*>)?.filterIsInstance<String>() ?: emptyList(),
                    expiresAt = (data["expiresAt"] as? Number)?.toLong(),
                    lastUsedAt = (data["lastUsedAt"] as? Number)?.toLong()
                )
            }
            emit(secrets)
        } catch (error: Exception) {
            Log.e("SecretsVault", "Failed to load server vault metadata.", error)
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
