package com.example.util

import android.content.Context
import android.util.Log
import com.example.BuildConfig
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

private const val TAG = "FirebaseRuntimeConfig"

/**
 * Initializes Firebase from build-time configuration when google-services.json
 * is not supplied. Never fabricates project identifiers or credentials.
 */
fun initializeFirebaseApp(context: Context): Boolean {
    if (FirebaseApp.getApps(context).isNotEmpty()) return true

    val required = mapOf(
        "FIREBASE_API_KEY" to BuildConfig.FIREBASE_API_KEY,
        "FIREBASE_APP_ID" to BuildConfig.FIREBASE_APP_ID,
        "FIREBASE_PROJECT_ID" to BuildConfig.FIREBASE_PROJECT_ID,
        "FIREBASE_MESSAGING_SENDER_ID" to BuildConfig.FIREBASE_MESSAGING_SENDER_ID
    )
    val missing = required.filterValues { it.isBlank() || it.startsWith("CONFIGURE_") }.keys
    if (missing.isNotEmpty()) {
        Log.w(TAG, "Firebase is not configured. Missing build values: ${missing.joinToString()}")
        return false
    }

    return try {
        val options = FirebaseOptions.Builder()
            .setApiKey(BuildConfig.FIREBASE_API_KEY)
            .setApplicationId(BuildConfig.FIREBASE_APP_ID)
            .setProjectId(BuildConfig.FIREBASE_PROJECT_ID)
            .setGcmSenderId(BuildConfig.FIREBASE_MESSAGING_SENDER_ID)
            .apply {
                if (BuildConfig.FIREBASE_STORAGE_BUCKET.isNotBlank()) {
                    setStorageBucket(BuildConfig.FIREBASE_STORAGE_BUCKET)
                }
                if (BuildConfig.FIREBASE_DATABASE_URL.isNotBlank()) {
                    setDatabaseUrl(BuildConfig.FIREBASE_DATABASE_URL)
                }
            }
            .build()
        FirebaseApp.initializeApp(context, options) != null
    } catch (error: Exception) {
        Log.e(TAG, "Firebase initialization failed.", error)
        false
    }
}
