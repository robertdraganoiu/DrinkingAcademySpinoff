package com.hack.drinkingacademy.user.data.repository

import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.user.data.mappers.toUserDetails
import com.hack.drinkingacademy.user.domain.model.UserDetails
import com.hack.drinkingacademy.user.domain.repository.UserDataSource

class SQLDelightUserDataSource(db: GameDatabase): UserDataSource {

    private val queries = db.gameQueries

    override suspend fun getUser(): UserDetails? {
        return queries.getUser()
            .executeAsOneOrNull()
            ?.toUserDetails()
    }

    override suspend fun updateUserScore(progress: Long) {
        queries.updateUserScore(progress)
    }

    override suspend fun updateUserSettings(soundLevel: Long, musicLevel: Long) {
        queries.updateUserSettings(soundLevel, musicLevel)
    }
}