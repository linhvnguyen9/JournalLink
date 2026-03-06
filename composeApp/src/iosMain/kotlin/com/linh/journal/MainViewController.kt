package com.linh.journal

import androidx.compose.ui.window.ComposeUIViewController
import com.linh.journal.data.getDatabaseBuilder
import com.linh.journal.data.getRoomDatabase

fun MainViewController() = ComposeUIViewController {
    val db = getRoomDatabase(getDatabaseBuilder())
    App(dao = db.journalDao())
}
