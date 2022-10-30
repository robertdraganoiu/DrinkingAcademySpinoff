package com.hack.drinkingacademy.user.data.repository

import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.user.data.mappers.toUserDetails
import com.hack.drinkingacademy.user.domain.model.UserDetails
import com.hack.drinkingacademy.user.domain.repository.UserDataSource

class SQLDelightUserDataSource(db: GameDatabase): UserDataSource {

    private val queries = db.gameQueries

    override fun getUser(): UserDetails? {
        return queries.getUser()
            .executeAsOneOrNull()
            ?.toUserDetails()
    }

    override fun updateUserScore(progress: Long) {
        queries.updateUserScore(progress)
    }

    override fun updateUserSettings(soundLevel: Long, musicLevel: Long) {
        queries.updateUserSettings(soundLevel, musicLevel)
    }
}