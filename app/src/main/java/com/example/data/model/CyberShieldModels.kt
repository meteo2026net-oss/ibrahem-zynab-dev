package com.example.data.model

import java.util.UUID

data class TrustedDevice(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val deviceName: String = "Android Secure Workstation",
    val operatingSystem: String = "Android 14 (API 34)",
    val ipAddress: String = "192.168.1.105",
    val biometricEnrolled: Boolean = true,
    val isAuthorized: Boolean = true,
    val lastLoginAt: Long = System.currentTimeMillis(),
    val trustScore: Int = 98
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "deviceName" to deviceName,
        "operatingSystem" to operatingSystem,
        "ipAddress" to ipAddress,
        "biometricEnrolled" to biometricEnrolled,
        "isAuthorized" to isAuthorized,
        "lastLoginAt" to lastLoginAt,
        "trustScore" to trustScore
    )
}

data class SecurityIncidentLog(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val severity: String = "INFO", // INFO, WARNING, CRITICAL
    val eventTitle: String = "",
    val description: String = "",
    val sourceDevice: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isResolved: Boolean = true
) {
    fun toFirestoreMap(): Map<String, Any?> = mapOf(
        "userId" to userId,
        "severity" to severity,
        "eventTitle" to eventTitle,
        "description" to description,
        "sourceDevice" to sourceDevice,
        "timestamp" to timestamp,
        "isResolved" to isResolved
    )
}
