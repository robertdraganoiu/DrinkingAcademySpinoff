package com.hack.drinkingacademy.common.local

import com.hack.drinkingacademy.database.GameDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(GameDatabase.Schema, "game.db")
    }
}