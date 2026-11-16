package com.example.data.repository

import android.content.Context
import com.example.data.model.SecretType
import com.example.data.model.VaultSecret
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Client facade for the Secrets Vault.
 *
 * The trusted server-side vault is not yet deployed. Direct client reads/writes and
 * client-side decryption are deliberately disabled; do not replace this with a
 * Firestore client write or an APK-embedded encryption key.
 */
class SecretsVaultRepository(
    @Suppress("unused") private val context: Context,
    @Suppress("unused") private val integrationsRepository: IntegrationsRepository
) {
    @Suppress("UNUSED_PARAMETER")
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
    ): VaultSecret {
        throw UnsupportedOperationException(
            "Secrets Vault is unavailable until the trusted backend is configured. No secret was stored."
        )
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun deleteSecret(secretId: String, projectId: String): Unit {
        throw UnsupportedOperationException(
            "Secrets Vault deletion requires the trusted backend. No client-side deletion was attempted."
        )
    }

    @Suppress("UNUSED_PARAMETER")
    suspend fun resolveSecretBackendOnly(
        referenceKey: String,
        requestingService: String,
        requestingAgent: String
    ): String? = null

    @Suppress("UNUSED_PARAMETER")
    fun observeSecrets(projectId: String): Flow<List<VaultSecret>> = flow<List<VaultSecret>> {
        emit(emptyList())
    }.flowOn(Dispatchers.IO)
}
