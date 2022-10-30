package com.hack.drinkingacademy.user.data.mappers

import com.hack.drinkingacademy.database.User
import com.hack.drinkingacademy.user.domain.model.UserDetails

fun User.toUserDetails(): UserDetails {
    return UserDetails(
        progress = progress,
        soundLevel = soundLevel,
        musicLevel = musicLevel
    )
}