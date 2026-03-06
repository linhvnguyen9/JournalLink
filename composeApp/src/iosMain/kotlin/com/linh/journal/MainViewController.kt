package com.linh.journal

import androidx.compose.ui.window.ComposeUIViewController
import com.linh.journal.di.initKoin
import com.linh.journal.di.platformModule

fun MainViewController() = run {
    initKoin {
        modules(platformModule)
    }
    ComposeUIViewController { App() }
}
