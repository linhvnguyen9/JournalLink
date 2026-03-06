package com.linh.journal.di

import com.linh.journal.data.AppDatabase
import com.linh.journal.data.getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single<androidx.room.RoomDatabase.Builder<AppDatabase>> { getDatabaseBuilder(androidContext()) }
}
