package com.linh.journal.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.linh.journal.data.dao.JournalDao
import com.linh.journal.data.entity.JournalEntry

@Database(entities = [JournalEntry::class], version = 1)
@ConstructedBy(TestJournalDatabaseConstructor::class)
internal abstract class TestJournalDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
}

@Suppress("KotlinNoActualForExpect")
internal expect object TestJournalDatabaseConstructor : RoomDatabaseConstructor<TestJournalDatabase>