package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.R
import com.example.data.model.SecretType
import com.example.data.model.VaultSecret
import com.example.data.security.CryptoUtils
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

class SecretsVaultRepository(
    private val context: Context,
    private val integrationsRepository: IntegrationsRepository
) {
    private val firestore by lazy {
        val databaseId = context.getString(R.string.firestore_database_id)
        Firebase.firestore(databaseId)
    }

    /**
     * Securely encrypts and stores a new secret in the Vault.
     * The raw plaintext secret is NEVER sent to AI prompts or LLM context windows.
     */
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
        val user = Firebase.auth.currentUser ?: throw IllegalStateException("Must be signed in.")
        val secretId = UUID.randomUUID().toString()
        val refKey = "SEC_REF_${secretName.uppercase().replace(" ", "_")}_${UUID.randomUUID().toString().take(6).uppercase()}"

        val encrypted = CryptoUtils.encrypt(plainSecretValue)
        val masked = CryptoUtils.maskSecret(plainSecretValue)

        val secret = VaultSecret(
            id = secretId,
            userId = user.uid,
            projectId = projectId,
            secretName = secretName,
            secretType = secretType.name,
            description = description,
            secretReferenceKey = refKey,
            encryptedSecretValue = encrypted,
            maskedSecretValue = masked,
            allowedServices = allowedServices,
            allowedAgents = allowedAgents,
            allowedOperations = allowedOperations,
            expiresAt = expiresAt
        )

        firestore.collection("project_secrets_vault")
            .document(secretId)
            .set(secret.toFirestoreMap())
            .await()

        integrationsRepository.logAudit(
            projectId,
            "VAULT_SECRET_ADDED",
            "Secret '$secretName' ($refKey) added securely to Secrets Vault"
        )

        secret
    }

    suspend fun deleteSecret(secretId: String, projectId: String) = withContext(Dispatchers.IO) {
        firestore.collection("project_secrets_vault").document(secretId).delete().await()
        integrationsRepository.logAudit(projectId, "VAULT_SECRET_DELETED", "Secret $secretId revoked and deleted")
    }

    /**
     * Resolves secret plaintext VALUE purely in Backend / Connector layer.
     * Checks permissions & expiration. Returns null if expired or missing.
     * CRITICAL SECURITY RULE: Never expose this resolved string to LLM prompts!
     */
    suspend fun resolveSecretBackendOnly(
        referenceKey: String,
        requestingService: String,
        requestingAgent: String
    ): String? = withContext(Dispatchers.IO) {
        val user = Firebase.auth.currentUser ?: return@withContext null
        try {
            val snapshot = firestore.collection("project_secrets_vault")
                .whereEqualTo("userId", user.uid)
                .whereEqualTo("secretReferenceKey", referenceKey)
                .get()
                .await()

            if (snapshot.isEmpty) return@withContext null

            val doc = snapshot.documents[0]
            val secret = doc.toObject(VaultSecret::class.java) ?: return@withContext null

            if (secret.isExpired()) {
                Log.w("SecretsVault", "Secret reference $referenceKey is expired!")
                return@withContext null
            }

            // Verify Service & Agent permission
            if (!secret.allowedServices.contains(requestingService) && !secret.allowedServices.contains("*")) {
                Log.w("SecretsVault", "Service $requestingService not authorized for $referenceKey")
                return@withContext null
            }

            // Update lastUsedAt timestamp
            doc.reference.update("lastUsedAt", System.currentTimeMillis()).await()

            integrationsRepository.logAudit(
                secret.projectId,
                "VAULT_SECRET_ACCESSED",
                "Secret ref '$referenceKey' accessed internally by service $requestingService / agent $requestingAgent"
            )

            CryptoUtils.decrypt(secret.encryptedSecretValue)
        } catch (e: Exception) {
            Log.e("SecretsVault", "Error resolving secret reference: ${e.message}")
            null
        }
    }

    fun observeSecrets(projectId: String): Flow<List<VaultSecret>> = flow {
        val user = Firebase.auth.currentUser
        if (user == null) {
            emit(emptyList())
            return@flow
        }
        try {
            val query = if (projectId.isBlank()) {
                firestore.collection("project_secrets_vault").whereEqualTo("userId", user.uid)
            } else {
                firestore.collection("project_secrets_vault").whereEqualTo("userId", user.uid).whereEqualTo("projectId", projectId)
            }

            val snapshot = query.get().await()
            val secrets = snapshot.toObjects(VaultSecret::class.java)
            emit(secrets)
        } catch (e: Exception) {
            Log.e("SecretsVault", "Error fetching secrets: ${e.message}")
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
