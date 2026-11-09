package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.AuditLogEntry
import com.example.data.model.Project
import com.example.data.model.ProjectStatus
import com.example.data.model.ProjectTask
import com.example.data.model.TaskStatus
import com.example.util.OperationType
import com.example.util.handleFirestoreError
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class ProjectRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User must be signed in with Google before accessing projects.")
    }

    fun observeProjects(): Flow<List<Project>> = flow {
        val uid = requireUserId()
        val path = "projects"
        emitAll(
            db.collection("projects")
                .whereEqualTo("userId", uid)
                .snapshots()
                .map { snapshot ->
                    snapshot.toObjects(Project::class.java)
                        .sortedByDescending { it.updatedAt ?: it.createdAt }
                }
                .catch { error ->
                    val firestoreError = generateSequence(error) { it.cause }
                        .filterIsInstance<FirebaseFirestoreException>()
                        .firstOrNull()
                    if (firestoreError == null && error is CancellationException) throw error
                    val exceptionToLog = firestoreError ?: (error as? Exception)
                    if (exceptionToLog != null && auth.currentUser?.uid == uid) {
                        handleFirestoreError(exceptionToLog, OperationType.LIST, path)
                    }
                    throw error
                }
        )
    }

    fun observeProject(projectId: String): Flow<Project?> = flow {
        val uid = requireUserId()
        val path = "projects/$projectId"
        emitAll(
            db.collection("projects").document(projectId)
                .snapshots()
                .map { snapshot ->
                    val proj = snapshot.toObject(Project::class.java)
                    if (proj != null && proj.userId == uid) proj else null
                }
                .catch { error ->
                    if (error is CancellationException) throw error
                    (error as? Exception)?.let { handleFirestoreError(it, OperationType.GET, path) }
                    throw error
                }
        )
    }

    suspend fun createProject(project: Project): String {
        val uid = requireUserId()
        val docRef = db.collection("projects").document()
        val payload = mapOf(
            "userId" to uid,
            "name" to project.name,
            "description" to project.description,
            "projectType" to project.projectType,
            "language" to project.language,
            "framework" to project.framework,
            "status" to project.status,
            "progress" to project.progress,
            "defaultAiProvider" to project.defaultAiProvider,
            "defaultAiModel" to project.defaultAiModel,
            "githubRepo" to project.githubRepo,
            "githubBranch" to project.githubBranch,
            "googleDriveFolderId" to project.googleDriveFolderId,
            "googleDriveAccountEmail" to project.googleDriveAccountEmail,
            "telegramBotId" to project.telegramBotId,
            "storageRouting" to mapOf(
                "codeDestination" to project.storageRouting.codeDestination,
                "docsDestination" to project.storageRouting.docsDestination,
                "mediaDestination" to project.storageRouting.mediaDestination,
                "backupsDestination" to project.storageRouting.backupsDestination
            ),
            "openTasksCount" to project.openTasksCount,
            "completedTasksCount" to project.completedTasksCount,
            "notes" to project.notes,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp()
        )
        try {
            docRef.set(payload).await()
            logAudit(docRef.id, "PROJECT_CREATED", "Project '${project.name}' initialized")
            return docRef.id
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }

    suspend fun updateProject(project: Project) {
        val uid = requireUserId()
        require(project.id.isNotBlank()) { "Project ID must not be blank" }
        val docRef = db.collection("projects").document(project.id)
        val payload = mapOf(
            "userId" to uid,
            "name" to project.name,
            "description" to project.description,
            "projectType" to project.projectType,
            "language" to project.language,
            "framework" to project.framework,
            "status" to project.status,
            "progress" to project.progress,
            "defaultAiProvider" to project.defaultAiProvider,
            "defaultAiModel" to project.defaultAiModel,
            "githubRepo" to project.githubRepo,
            "githubBranch" to project.githubBranch,
            "googleDriveFolderId" to project.googleDriveFolderId,
            "googleDriveAccountEmail" to project.googleDriveAccountEmail,
            "telegramBotId" to project.telegramBotId,
            "storageRouting" to mapOf(
                "codeDestination" to project.storageRouting.codeDestination,
                "docsDestination" to project.storageRouting.docsDestination,
                "mediaDestination" to project.storageRouting.mediaDestination,
                "backupsDestination" to project.storageRouting.backupsDestination
            ),
            "openTasksCount" to project.openTasksCount,
            "completedTasksCount" to project.completedTasksCount,
            "notes" to project.notes,
            "updatedAt" to FieldValue.serverTimestamp()
        )
        try {
            docRef.update(payload).await()
            logAudit(project.id, "PROJECT_UPDATED", "Project '${project.name}' settings updated")
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            throw e
        }
    }

    suspend fun toggleArchiveProject(projectId: String, shouldArchive: Boolean) {
        val uid = requireUserId()
        val docRef = db.collection("projects").document(projectId)
        val newStatus = if (shouldArchive) ProjectStatus.ARCHIVED.name else ProjectStatus.ACTIVE.name
        try {
            docRef.update(
                mapOf(
                    "status" to newStatus,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            logAudit(projectId, "ARCHIVE_TOGGLED", "Project status set to $newStatus")
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            throw e
        }
    }

    suspend fun deleteProject(projectId: String) {
        val uid = requireUserId()
        val docRef = db.collection("projects").document(projectId)
        try {
            docRef.delete().await()
            logAudit(projectId, "PROJECT_DELETED", "Project deleted by user $uid")
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            throw e
        }
    }

    suspend fun cloneProjectWithoutSecrets(source: Project, newName: String): String {
        val cloned = source.copy(
            id = "",
            name = newName,
            status = ProjectStatus.PLANNING.name,
            progress = 0,
            openTasksCount = 0,
            completedTasksCount = 0,
            createdAt = null,
            updatedAt = null
        )
        val newId = createProject(cloned)
        logAudit(newId, "PROJECT_CLONED", "Cloned configuration from '${source.name}' without secret credentials")
        return newId
    }

    fun exportProjectNonSecretConfig(project: Project): String {
        return """
        {
          "metraProjectVersion": "1.0",
          "name": "${project.name}",
          "description": "${project.description}",
          "type": "${project.projectType}",
          "language": "${project.language}",
          "framework": "${project.framework}",
          "defaultAiProvider": "${project.defaultAiProvider}",
          "defaultAiModel": "${project.defaultAiModel}",
          "githubRepo": "${project.githubRepo}",
          "githubBranch": "${project.githubBranch}",
          "storageRouting": {
            "code": "${project.storageRouting.codeDestination}",
            "docs": "${project.storageRouting.docsDestination}",
            "media": "${project.storageRouting.mediaDestination}",
            "backups": "${project.storageRouting.backupsDestination}"
          }
        }
        """.trimIndent()
    }

    // Tasks Management
    fun observeTasks(projectId: String): Flow<List<ProjectTask>> = flow {
        val uid = requireUserId()
        val path = "tasks"
        emitAll(
            db.collection("tasks")
                .whereEqualTo("userId", uid)
                .whereEqualTo("projectId", projectId)
                .snapshots()
                .map { snapshot -> snapshot.toObjects(ProjectTask::class.java) }
                .catch { error ->
                    if (error is CancellationException) throw error
                    (error as? Exception)?.let { handleFirestoreError(it, OperationType.LIST, path) }
                    throw error
                }
        )
    }

    suspend fun addTask(task: ProjectTask) {
        val uid = requireUserId()
        val docRef = db.collection("tasks").document()
        val payload = mapOf(
            "userId" to uid,
            "projectId" to task.projectId,
            "title" to task.title,
            "description" to task.description,
            "assignedAgent" to task.assignedAgent,
            "status" to task.status,
            "priority" to task.priority,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp()
        )
        try {
            docRef.set(payload).await()
            updateTaskCounts(task.projectId)
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }

    suspend fun updateTaskStatus(taskId: String, projectId: String, newStatus: TaskStatus) {
        val docRef = db.collection("tasks").document(taskId)
        try {
            docRef.update(
                mapOf(
                    "status" to newStatus.name,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
            updateTaskCounts(projectId)
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            throw e
        }
    }

    suspend fun deleteTask(taskId: String, projectId: String) {
        val docRef = db.collection("tasks").document(taskId)
        try {
            docRef.delete().await()
            updateTaskCounts(projectId)
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            throw e
        }
    }

    private suspend fun updateTaskCounts(projectId: String) {
        val uid = requireUserId()
        try {
            val snapshot = db.collection("tasks")
                .whereEqualTo("userId", uid)
                .whereEqualTo("projectId", projectId)
                .get().await()
            val tasks = snapshot.toObjects(ProjectTask::class.java)
            val open = tasks.count { it.status != TaskStatus.DONE.name }
            val completed = tasks.count { it.status == TaskStatus.DONE.name }
            val total = tasks.size
            val progress = if (total > 0) ((completed.toDouble() / total) * 100).toInt() else 0

            db.collection("projects").document(projectId).update(
                mapOf(
                    "openTasksCount" to open,
                    "completedTasksCount" to completed,
                    "progress" to progress,
                    "updatedAt" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (e: Exception) {
            // non-fatal count sync
        }
    }

    private suspend fun logAudit(projectId: String, action: String, details: String) {
        try {
            val uid = requireUserId()
            val docRef = db.collection("audit_logs").document()
            docRef.set(
                mapOf(
                    "userId" to uid,
                    "projectId" to projectId,
                    "action" to action,
                    "details" to details,
                    "severity" to "INFO",
                    "timestamp" to FieldValue.serverTimestamp()
                )
            ).await()
        } catch (_: Exception) {}
    }
}
