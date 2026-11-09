package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.UUID

enum class SecretType(val displayNameAr: String) {
    DATABASE_PASSWORD("كلمة مرور قاعدة بيانات"),
    DATABASE_CONNECTION_STRING("سلسلة اتصال قاعدة البيانات"),
    API_KEY("مفتاح API"),
    GITHUB_TOKEN("رمز GitHub Access Token"),
    GOOGLE_OAUTH_CREDENTIAL("بيانات اعتماد Google OAuth"),
    TELEGRAM_BOT_TOKEN("رمز بوت Telegram"),
    HOSTING_PASSWORD("كلمة مرور الاستضافة / SSH"),
    INTEGRATION_ENCRYPTION_KEY("مفتاح تشفير التكاملات"),
    SMTP_CREDENTIALS("بيانات اعتماد SMTP البريد"),
    ENV_VARIABLE("متغير بيئة سري"),
    OTHER_SECRET("سر آخر")
}

@IgnoreExtraProperties
data class VaultSecret(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val secretName: String = "",
    val secretType: String = SecretType.API_KEY.name,
    val description: String = "",
    val secretReferenceKey: String = "SEC_REF_${UUID.randomUUID().toString().take(12).uppercase()}",
    val encryptedSecretValue: String = "",
    val maskedSecretValue: String = "••••••••",
    val allowedServices: List<String> = listOf("DATABASE_CONNECTOR", "GITHUB_SERVICE", "BACKEND_WORKER"),
    val allowedAgents: List<String> = listOf("CODE_DEVELOPER", "TESTING_QA"),
    val allowedOperations: List<String> = listOf("READ_SCHEMA", "EXECUTE_QUERY", "CONNECT"),
    val expiresAt: Long? = null,
    val lastUsedAt: Long? = null,
    @ServerTimestamp val createdAt: Timestamp? = null
) {
    fun isExpired(): Boolean {
        val exp = expiresAt ?: return false
        return System.currentTimeMillis() > exp
    }

    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "projectId" to projectId,
        "secretName" to secretName,
        "secretType" to secretType,
        "description" to description,
        "secretReferenceKey" to secretReferenceKey,
        "encryptedSecretValue" to encryptedSecretValue,
        "maskedSecretValue" to maskedSecretValue,
        "allowedServices" to allowedServices,
        "allowedAgents" to allowedAgents,
        "allowedOperations" to allowedOperations,
        "expiresAt" to expiresAt,
        "lastUsedAt" to lastUsedAt,
        "createdAt" to (createdAt ?: com.google.firebase.firestore.FieldValue.serverTimestamp())
    )
}

enum class DatabaseEngine(val displayName: String, val defaultPort: Int) {
    POSTGRESQL("PostgreSQL", 5432),
    SUPABASE("Supabase", 5432),
    MYSQL("MySQL", 3306),
    MSSQL("Microsoft SQL Server", 1433),
    SQLITE("SQLite (Embedded)", 0),
    MONGODB("MongoDB", 27017),
    REDIS("Redis (Key-Value)", 6379)
}

@IgnoreExtraProperties
data class DatabaseConnectionConfig(
    @DocumentId val id: String = "",
    val userId: String = "",
    val projectId: String = "",
    val connectionName: String = "",
    val engineType: String = DatabaseEngine.POSTGRESQL.name,
    val host: String = "localhost",
    val port: Int = 5432,
    val databaseName: String = "",
    val username: String = "",
    val secretReferenceKey: String = "",
    val isReadOnly: Boolean = true,
    val allowedSchemas: List<String> = listOf("public"),
    val queryTimeoutSeconds: Int = 30,
    val status: String = "CONNECTED",
    val lastTestedAt: Long = System.currentTimeMillis(),
    @ServerTimestamp val createdAt: Timestamp? = null
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "projectId" to projectId,
        "connectionName" to connectionName,
        "engineType" to engineType,
        "host" to host,
        "port" to port,
        "databaseName" to databaseName,
        "username" to username,
        "secretReferenceKey" to secretReferenceKey,
        "isReadOnly" to isReadOnly,
        "allowedSchemas" to allowedSchemas,
        "queryTimeoutSeconds" to queryTimeoutSeconds,
        "status" to status,
        "lastTestedAt" to lastTestedAt,
        "createdAt" to (createdAt ?: com.google.firebase.firestore.FieldValue.serverTimestamp())
    )
}

data class DatabaseColumnSchema(
    val columnName: String,
    val dataType: String,
    val isNullable: Boolean,
    val isPrimaryKey: Boolean,
    val isForeignKey: Boolean,
    val foreignTable: String? = null
)

data class DatabaseTableSchema(
    val tableName: String,
    val schemaName: String = "public",
    val columns: List<DatabaseColumnSchema>,
    val rowCountEstimate: Long = 0,
    val missingIndexes: List<String> = emptyList()
)

data class SqlQueryResult(
    val isSuccess: Boolean,
    val columns: List<String> = emptyList(),
    val rows: List<List<String>> = emptyList(),
    val affectedRows: Int = 0,
    val executionTimeMs: Long = 0,
    val errorMessage: String? = null
)
