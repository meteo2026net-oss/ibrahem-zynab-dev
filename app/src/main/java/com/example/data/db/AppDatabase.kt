package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.ProjectMemory
import com.example.data.model.SemanticContextSnippet

@Database(entities = [ProjectMemory::class, SemanticContextSnippet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectMemoryDao(): ProjectMemoryDao
    abstract fun semanticContextDao(): SemanticContextDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "metra_ai_control_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
