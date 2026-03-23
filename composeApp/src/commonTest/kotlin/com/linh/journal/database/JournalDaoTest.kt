package com.linh.journal.database

import com.linh.journal.data.entity.JournalEntry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JournalDaoTest {

    private lateinit var dao: com.linh.journal.data.dao.JournalDao

    @BeforeTest
    fun setup() {
        dao = buildTestJournalDao()
    }

    @Test
    fun insertAndGetById() = runBlocking {
        val entry = JournalEntry(
            title = "Test Title",
            content = "Test Content",
            createdAt = 1000L,
            updatedAt = 1000L
        )
        dao.insert(entry)

        val all = dao.getAllAsFlow().first()
        assertEquals(1, all.size)

        val fetched = dao.getById(all.first().id)
        assertNotNull(fetched)
        assertEquals("Test Title", fetched.title)
        assertEquals("Test Content", fetched.content)
    }

    @Test
    fun getAllAsFlowReturnsOrderedByUpdatedAtDesc() = runBlocking {
        dao.insert(JournalEntry(title = "Old", content = "", createdAt = 100L, updatedAt = 100L))
        dao.insert(JournalEntry(title = "New", content = "", createdAt = 200L, updatedAt = 300L))
        dao.insert(JournalEntry(title = "Mid", content = "", createdAt = 150L, updatedAt = 200L))

        val all = dao.getAllAsFlow().first()
        assertEquals(3, all.size)
        assertEquals("New", all[0].title)
        assertEquals("Mid", all[1].title)
        assertEquals("Old", all[2].title)
    }

    @Test
    fun updateEntry() = runBlocking {
        dao.insert(JournalEntry(title = "Original", content = "Body", createdAt = 1L, updatedAt = 1L))
        val inserted = dao.getAllAsFlow().first().first()

        val updated = inserted.copy(title = "Updated", updatedAt = 2L)
        dao.update(updated)

        val fetched = dao.getById(inserted.id)
        assertNotNull(fetched)
        assertEquals("Updated", fetched.title)
        assertEquals(2L, fetched.updatedAt)
        assertEquals("Body", fetched.content)
    }

    @Test
    fun deleteEntry() = runBlocking {
        dao.insert(JournalEntry(title = "To Delete", content = "", createdAt = 1L, updatedAt = 1L))
        val inserted = dao.getAllAsFlow().first().first()

        dao.delete(inserted)

        val fetched = dao.getById(inserted.id)
        assertNull(fetched)
        assertTrue(dao.getAllAsFlow().first().isEmpty())
    }

    @Test
    fun getByIdReturnsNullForNonExistentId() = runBlocking {
        val result = dao.getById(999L)
        assertNull(result)
    }

    @Test
    fun insertMultipleEntries() = runBlocking {
        repeat(5) { i ->
            dao.insert(
                JournalEntry(
                    title = "Entry $i",
                    content = "Content $i",
                    createdAt = i.toLong(),
                    updatedAt = i.toLong()
                )
            )
        }

        val all = dao.getAllAsFlow().first()
        assertEquals(5, all.size)
    }
}