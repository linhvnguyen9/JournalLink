package com.linh.journal.database

import com.linh.journal.data.buildTestDatabase

actual fun buildTestJournalDao(): com.linh.journal.data.dao.JournalDao {
    return buildTestDatabase<TestJournalDatabase>()
        .journalDao()
}