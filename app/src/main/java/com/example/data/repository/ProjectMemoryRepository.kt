package com.example.data.repository

import android.content.Context
import com.example.data.db.AppDatabase
import com.example.data.model.ProjectMemory
import com.example.data.model.SemanticContextSnippet
import kotlinx.coroutines.flow.Flow

class ProjectMemoryRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val memoryDao = db.projectMemoryDao()
    private val contextDao = db.semanticContextDao()

    fun getMemoriesForProject(projectId: String): Flow<List<ProjectMemory>> =
        memoryDao.getMemoriesForProject(projectId)

    suspend fun addMemory(memory: ProjectMemory) {
        memoryDao.insertMemory(memory)
    }

    suspend fun updateMemory(memory: ProjectMemory) {
        memoryDao.updateMemory(memory)
    }

    suspend fun deleteMemory(id: Long) {
        memoryDao.deleteMemory(id)
    }

    /**
     * Semantic Search (Simulated via keyword and tag relevance)
     * Queries memories based on task context.
     */
    suspend fun findRelevantContext(projectId: String, taskQuery: String): String {
        val keywords = taskQuery.split(" ").filter { it.length > 3 }
        val allRelevantMemories = mutableListOf<ProjectMemory>()
        
        keywords.forEach { keyword ->
            val results = memoryDao.searchMemories(projectId, keyword)
            allRelevantMemories.addAll(results)
        }

        val uniqueMemories = allRelevantMemories.distinctBy { it.id }
            .sortedByDescending { it.importance }
            .take(5)

        if (uniqueMemories.isEmpty()) return "لا توجد ذاكرة سابقة متعلقة بهذا المشروع بعد."

        return buildString {
            appendLine("بناءً على ذاكرة المشروع:")
            uniqueMemories.forEach { memory ->
                appendLine("- [${memory.category}] ${memory.key}: ${memory.content}")
            }
        }
    }

    // Semantic Context Snippets (for code history tracking)
    fun getContextHistory(projectId: String): Flow<List<SemanticContextSnippet>> =
        contextDao.getContextHistory(projectId)

    suspend fun recordSnippet(snippet: SemanticContextSnippet) {
        contextDao.insertSnippet(snippet)
    }
}
