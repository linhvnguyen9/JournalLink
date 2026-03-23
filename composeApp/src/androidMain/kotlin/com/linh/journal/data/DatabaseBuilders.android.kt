package com.linh.journal.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.Dispatchers

inline fun <reified T : RoomDatabase> buildTestDatabase(): T {
    return Room.inMemoryDatabaseBuilder<T>(
        context = ApplicationProvider.getApplicationContext(),
    )
        .allowMainThreadQueries()
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}