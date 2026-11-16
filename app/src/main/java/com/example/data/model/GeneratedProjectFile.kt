package com.example.data.model

/**
 * A validated text file returned by the trusted project-generation worker.
 * File contents are data only; callers must not execute them automatically.
 */
data class GeneratedProjectFile(
    val path: String,
    val content: String
)
