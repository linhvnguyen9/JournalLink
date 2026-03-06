package com.linh.journal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.linh.journal.data.getDatabaseBuilder
import com.linh.journal.data.getRoomDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val db = getRoomDatabase(getDatabaseBuilder(this))

        setContent {
            App(dao = db.journalDao())
        }
    }
}
