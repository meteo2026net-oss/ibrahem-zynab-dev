package com.example.data.security

import java.security.MessageDigest

/**
 * Client-side secret encryption is intentionally disabled.
 *
 * A key embedded in an APK is recoverable by anyone who can inspect the package.
 * Secrets must be encrypted/decrypted by a trusted server using managed key storage.
 * These methods fail closed so callers cannot accidentally persist plaintext.
 */
object CryptoUtils {
    fun encrypt(plainText: String): String {
        if (plainText.isBlank()) return ""
        throw UnsupportedOperationException(
            "Client-side secret encryption is disabled. Configure the trusted Secrets Vault backend."
        )
    }

    fun decrypt(encryptedText: String): String {
        if (encryptedText.isBlank()) return ""
        throw UnsupportedOperationException(
            "Client-side secret decryption is disabled. Resolve secrets through the trusted backend."
        )
    }

    fun maskSecret(secret: String): String {
        if (secret.length <= 4) return "••••"
        val last4 = secret.takeLast(4)
        return "••••••••$last4"
    }

    fun calculateSha256(content: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(content)
        return hash.joinToString("") { "%02x".format(it) }
    }
}
