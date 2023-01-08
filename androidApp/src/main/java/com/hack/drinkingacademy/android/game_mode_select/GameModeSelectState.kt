package com.hack.drinkingacademy.android.game_mode_select

import com.hack.drinkingacademy.game.domain.model.GameMode
import com.hack.drinkingacademy.user.domain.model.UserDetails

data class GamePrepState (
    val user: UserDetails? = null,
    val players: List<String> = emptyList(),
    val gameModes: List<GameMode> = emptyList()
)