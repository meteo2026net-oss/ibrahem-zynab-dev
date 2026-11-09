package com.example.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException

enum class OperationType {
    GET, LIST, CREATE, UPDATE, DELETE
}

fun handleFirestoreError(e: Exception, operation: OperationType, path: String) {
    val code = if (e is FirebaseFirestoreException) e.code.name else "UNKNOWN"
    Log.e(
        "FirestoreError",
        "Firestore operation $operation failed at '$path' with code $code: ${e.localizedMessage}",
        e
    )
}
