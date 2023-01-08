package com.hack.drinkingacademy.user.domain.repository

import com.hack.drinkingacademy.user.domain.model.UserDetails

interface UserDataSource {
    suspend fun getUser(): UserDetails?
    suspend fun updateUserScore(progress: Long)
    suspend fun updateUserSettings(soundLevel: Long, musicLevel: Long)
}