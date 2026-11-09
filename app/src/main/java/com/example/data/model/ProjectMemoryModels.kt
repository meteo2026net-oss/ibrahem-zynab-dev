package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.Converters

@Entity(tableName = "project_memories")
data class ProjectMemory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val projectId: String,
    val category: String, // Architecture, Goals, Dependencies, UI_Style, Security_Decision
    val key: String,      // e.g., "PrimaryColor", "DatabaseType", "AuthMethod"
    val content: String,
    val tags: String,     // Comma separated tags for search
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val importance: Int = 1 // 1-5 scale
)

@Entity(tableName = "semantic_context_snippets")
data class SemanticContextSnippet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val projectId: String,
    val fileName: String,
    val codeSnippet: String,
    val explanation: String,
    val timestamp: Long = System.currentTimeMillis()
)
