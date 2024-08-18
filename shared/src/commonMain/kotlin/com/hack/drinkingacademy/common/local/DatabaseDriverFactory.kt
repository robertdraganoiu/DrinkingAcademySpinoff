package com.hack.drinkingacademy.common.local

import com.hack.drinkingacademy.common.constants.Constants
import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(dbName: String = Constants.DB_NAME): SqlDriver
}