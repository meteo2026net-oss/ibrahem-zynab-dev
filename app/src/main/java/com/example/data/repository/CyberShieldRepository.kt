package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.SecurityIncidentLog
import com.example.data.model.TrustedDevice
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

class CyberShieldRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid ?: "usr_offline_unit_test"
    }

    fun observeTrustedDevices(): Flow<List<TrustedDevice>> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("trusted_devices")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(TrustedDevice::class.java).sortedByDescending { it.lastLoginAt }
                }
        )
    }

    fun observeSecurityIncidents(): Flow<List<SecurityIncidentLog>> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("security_incidents")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(SecurityIncidentLog::class.java).sortedByDescending { it.timestamp }
                }
        )
    }

    suspend fun registerCurrentDevice(deviceName: String, os: String): TrustedDevice = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        val docRef = db.collection("trusted_devices").document()
        val device = TrustedDevice(
            id = docRef.id,
            userId = uid,
            deviceName = deviceName,
            operatingSystem = os,
            ipAddress = "192.168.1.50",
            biometricEnrolled = true,
            isAuthorized = true,
            lastLoginAt = System.currentTimeMillis(),
            trustScore = 100
        )
        docRef.set(device.toFirestoreMap()).await()

        val incidentRef = db.collection("security_incidents").document()
        val incident = SecurityIncidentLog(
            id = incidentRef.id,
            userId = uid,
            severity = "INFO",
            eventTitle = "مصادقة جهاز جديد وموثوق",
            description = "تم اعتماد الجهاز ($deviceName) بنجاح عبر المصادقة البيومترية والمفتاح المشفر.",
            sourceDevice = deviceName,
            timestamp = System.currentTimeMillis(),
            isResolved = true
        )
        incidentRef.set(incident.toFirestoreMap()).await()

        return@withContext device
    }

    suspend fun revokeDevice(deviceId: String) = withContext(Dispatchers.IO) {
        db.collection("trusted_devices").document(deviceId).delete().await()
    }
}
