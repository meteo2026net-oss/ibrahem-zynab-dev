package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ProjectMemory
import com.example.data.model.SemanticContextSnippet
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectMemoryDao {
    @Query("SELECT * FROM project_memories WHERE projectId = :projectId ORDER BY importance DESC, updatedAt DESC")
    fun getMemoriesForProject(projectId: String): Flow<List<ProjectMemory>>

    @Query("SELECT * FROM project_memories WHERE projectId = :projectId AND (content LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%')")
    suspend fun searchMemories(projectId: String, query: String): List<ProjectMemory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: ProjectMemory)

    @Update
    suspend fun updateMemory(memory: ProjectMemory)

    @Query("DELETE FROM project_memories WHERE id = :id")
    suspend fun deleteMemory(id: Long)
}

@Dao
interface SemanticContextDao {
    @Query("SELECT * FROM semantic_context_snippets WHERE projectId = :projectId ORDER BY timestamp DESC LIMIT 50")
    fun getContextHistory(projectId: String): Flow<List<SemanticContextSnippet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnippet(snippet: SemanticContextSnippet)

    @Query("DELETE FROM semantic_context_snippets WHERE projectId = :projectId")
    suspend fun clearProjectContext(projectId: String)
}
