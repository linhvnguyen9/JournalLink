package com.linh.journal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)
