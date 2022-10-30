package com.hack.drinkingacademy.common.local

import android.content.Context
import com.hack.drinkingacademy.database.GameDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(GameDatabase.Schema, context, "game.db")
    }
}