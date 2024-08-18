package com.hack.drinkingacademy.common.local

import android.content.Context
import com.hack.drinkingacademy.database.GameDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import java.io.FileOutputStream

actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(dbName: String): SqlDriver {
        copyDatabaseFromAssetsIfNeeded(dbName)
        return AndroidSqliteDriver(GameDatabase.Schema, context, dbName)
    }

    private fun copyDatabaseFromAssetsIfNeeded(dbName: String) {
        val dbPath = context.getDatabasePath(dbName)

        if (!dbPath.exists()) {
            context.assets.open(dbName).use { inputStream ->
                FileOutputStream(dbPath).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }
}
