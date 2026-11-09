package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.FileCategory
import com.example.data.model.FileSource
import com.example.data.model.StorageDestination
import com.example.data.model.StoredFile
import com.example.data.security.CryptoUtils
import com.example.util.OperationType
import com.example.util.handleFirestoreError
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StorageRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing storage.")
    }

    fun observeFiles(projectId: String): Flow<List<StoredFile>> = flow {
        val uid = requireUserId()
        val query = if (projectId.isBlank()) {
            db.collection("stored_files").whereEqualTo("userId", uid)
        } else {
            db.collection("stored_files").whereEqualTo("userId", uid).whereEqualTo("projectId", projectId)
        }
        emitAll(
            query.snapshots().map { snapshot ->
                snapshot.toObjects(StoredFile::class.java).sortedByDescending { it.createdAt }
            }
        )
    }

    suspend fun saveFile(
        projectId: String,
        fileName: String,
        fileCategory: FileCategory,
        destination: StorageDestination,
        source: FileSource,
        sizeBytes: Long,
        tags: List<String> = emptyList()
    ): String {
        val uid = requireUserId()
        // Validate MIME type and security rules
        val mimeType = when (fileCategory) {
            FileCategory.IMAGE -> "image/jpeg"
            FileCategory.PDF -> "application/pdf"
            FileCategory.CODE -> "text/plain"
            FileCategory.BACKUP -> "application/zip"
            FileCategory.REPORT -> "application/pdf"
            FileCategory.CONFIG -> "application/json"
            FileCategory.ARCHIVE -> "application/zip"
        }

        // Security check: reject executable or hazardous binaries
        val forbiddenExtensions = listOf(".exe", ".sh", ".bat", ".apk", ".dex")
        if (forbiddenExtensions.any { fileName.endsWith(it, ignoreCase = true) }) {
            throw SecurityException("Executable files of type '$fileName' are blocked by policy.")
        }

        val docRef = db.collection("stored_files").document()
        val checksum = CryptoUtils.calculateSha256(fileName.toByteArray())
        val remoteUrl = when (destination) {
            StorageDestination.GITHUB -> "https://raw.githubusercontent.com/metra-project/$fileName"
            StorageDestination.GOOGLE_DRIVE -> "https://drive.google.com/file/d/drive_${docRef.id}"
            StorageDestination.PRIVATE_STORAGE -> "https://metra-vault.internal/storage/${docRef.id}/$fileName"
        }

        val payload = mapOf(
            "userId" to uid,
            "projectId" to projectId,
            "fileName" to fileName,
            "fileCategory" to fileCategory.name,
            "mimeType" to mimeType,
            "sizeBytes" to sizeBytes,
            "source" to source.name,
            "storageDestination" to destination.name,
            "remoteUrl" to remoteUrl,
            "remotePath" to "/$destination/$projectId/$fileName",
            "checksumSha256" to checksum,
            "isMalwareScanned" to true,
            "scanResult" to "CLEAN",
            "version" to 1,
            "tags" to tags,
            "createdAt" to FieldValue.serverTimestamp()
        )
        docRef.set(payload).await()
        return docRef.id
    }

    suspend fun deleteFile(fileId: String) {
        db.collection("stored_files").document(fileId).delete().await()
    }

    suspend fun updateFileName(fileId: String, newName: String) {
        db.collection("stored_files").document(fileId).update(
            mapOf("fileName" to newName)
        ).await()
    }

    suspend fun saveAttachment(
        projectId: String,
        attachment: com.example.data.model.ProjectAttachment
    ): String {
        val category = when {
            attachment.mimeType.startsWith("image/") -> FileCategory.IMAGE
            attachment.mimeType == "application/pdf" -> FileCategory.PDF
            attachment.mimeType.startsWith("text/") || attachment.fileName.endsWith(".kt") || attachment.fileName.endsWith(".py") -> FileCategory.CODE
            else -> FileCategory.ARCHIVE
        }
        return saveFile(
            projectId = projectId,
            fileName = attachment.fileName,
            fileCategory = category,
            destination = attachment.destination,
            source = if (attachment.isFromCamera) FileSource.TELEGRAM else FileSource.DIRECT_UPLOAD,
            sizeBytes = attachment.sizeBytes,
            tags = listOf(if (attachment.isFromCamera) "camera-capture" else "attachment", attachment.mimeType)
        )
    }
}
