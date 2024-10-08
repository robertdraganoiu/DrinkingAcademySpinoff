package com.hack.drinkingacademy.android.di

import android.app.Application
import com.hack.drinkingacademy.common.local.DatabaseDriverFactory
import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.game.GameDataSource
import com.hack.drinkingacademy.game.impl.SQLDelightGameDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSQLDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideGameDataSource(driver: SqlDriver) : GameDataSource {
        return SQLDelightGameDataSource(GameDatabase(driver))
    }
}