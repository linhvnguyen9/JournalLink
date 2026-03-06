package com.linh.journal.di

import com.linh.journal.JournalViewModel
import com.linh.journal.data.AppDatabase
import com.linh.journal.data.getRoomDatabase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> { getRoomDatabase(get()) }
    single { get<AppDatabase>().journalDao() }
    viewModel { JournalViewModel(get()) }
}
