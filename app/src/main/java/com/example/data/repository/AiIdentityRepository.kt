package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.AiIdentityConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AiIdentityRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid ?: "usr_offline_unit_test"
    }

    fun observeIdentityConfig(): Flow<AiIdentityConfig> = flow {
        val uid = requireUserId()
        emitAll(
            db.collection("ai_identity_config").document(uid).snapshots().map { snapshot ->
                if (snapshot.exists()) {
                    val cfg = snapshot.toObject(AiIdentityConfig::class.java)
                    cfg ?: AiIdentityConfig()
                } else {
                    AiIdentityConfig()
                }
            }
        )
    }

    suspend fun saveIdentityConfig(config: AiIdentityConfig) = withContext(Dispatchers.IO) {
        val uid = requireUserId()
        db.collection("ai_identity_config").document(uid).set(config.toFirestoreMap()).await()
    }
}
