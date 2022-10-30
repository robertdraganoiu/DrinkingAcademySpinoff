package com.hack.drinkingacademy.android.di

import android.app.Application
import com.hack.drinkingacademy.common.local.DatabaseDriverFactory
import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.dbloader.data.repository.SQLDelightGameDataLoader
import com.hack.drinkingacademy.dbloader.domain.repository.GameDataLoader
import com.hack.drinkingacademy.game.data.repository.SQLDelightGameDataSource
import com.hack.drinkingacademy.game.domain.repository.GameDataSource
import com.hack.drinkingacademy.user.data.repository.SQLDelightUserDataSource
import com.hack.drinkingacademy.user.domain.repository.UserDataSource
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
    fun provideUserDataSource(driver: SqlDriver) : UserDataSource {
        return SQLDelightUserDataSource(GameDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideGameDbLoader(driver: SqlDriver) : GameDataLoader {
        return SQLDelightGameDataLoader(GameDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideGameDataSource(driver: SqlDriver) : GameDataSource {
        return SQLDelightGameDataSource(GameDatabase(driver))
    }
}