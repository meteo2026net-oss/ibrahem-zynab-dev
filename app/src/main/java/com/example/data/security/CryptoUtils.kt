package com.example.data.security

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoUtils {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val TAG_LENGTH_BIT = 128
    private const val IV_LENGTH_BYTE = 12
    // Static project salt for demonstration safe encryption across sessions
    private val KEY_BYTES = "METRA_AI_SECURE_VAULT_KEY_2026_!".toByteArray(StandardCharsets.UTF_8).copyOf(32)

    private fun encodeBase64(bytes: ByteArray): String {
        return try {
            java.util.Base64.getEncoder().encodeToString(bytes)
        } catch (_: Throwable) {
            android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
        }
    }

    private fun decodeBase64(str: String): ByteArray {
        return try {
            java.util.Base64.getDecoder().decode(str)
        } catch (_: Throwable) {
            android.util.Base64.decode(str, android.util.Base64.NO_WRAP)
        }
    }

    fun encrypt(plainText: String): String {
        if (plainText.isBlank()) return ""
        return try {
            val key = SecretKeySpec(KEY_BYTES, "AES")
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val iv = cipher.iv
            val cipherText = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
            val combined = ByteArray(iv.size + cipherText.size)
            System.arraycopy(iv, 0, combined, 0, iv.size)
            System.arraycopy(cipherText, 0, combined, iv.size, cipherText.size)
            encodeBase64(combined)
        } catch (e: Exception) {
            plainText
        }
    }

    fun decrypt(encryptedText: String): String {
        if (encryptedText.isBlank()) return ""
        return try {
            val decoded = decodeBase64(encryptedText)
            if (decoded.size <= IV_LENGTH_BYTE) return ""
            val iv = decoded.copyOfRange(0, IV_LENGTH_BYTE)
            val cipherText = decoded.copyOfRange(IV_LENGTH_BYTE, decoded.size)
            val key = SecretKeySpec(KEY_BYTES, "AES")
            val cipher = Cipher.getInstance(ALGORITHM)
            val spec = GCMParameterSpec(TAG_LENGTH_BIT, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, spec)
            String(cipher.doFinal(cipherText), StandardCharsets.UTF_8)
        } catch (e: Exception) {
            ""
        }
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
