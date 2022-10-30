package com.hack.drinkingacademy.user.domain.repository

import com.hack.drinkingacademy.user.domain.model.UserDetails

interface UserDataSource {
    fun getUser(): UserDetails?
    fun updateUserScore(progress: Long)
    fun updateUserSettings(soundLevel: Long, musicLevel: Long)
}