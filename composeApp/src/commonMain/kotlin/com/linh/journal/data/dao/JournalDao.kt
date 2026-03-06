package com.linh.journal.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.linh.journal.data.entity.JournalEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Insert
    suspend fun insert(entry: JournalEntry)

    @Update
    suspend fun update(entry: JournalEntry)

    @Delete
    suspend fun delete(entry: JournalEntry)

    @Query("SELECT * FROM JournalEntry ORDER BY updatedAt DESC")
    fun getAllAsFlow(): Flow<List<JournalEntry>>

    @Query("SELECT * FROM JournalEntry WHERE id = :id")
    suspend fun getById(id: Long): JournalEntry?
}
