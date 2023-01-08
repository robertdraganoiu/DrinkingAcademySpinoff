package com.hack.drinkingacademy.android.game_mode_select

import com.hack.drinkingacademy.game.domain.model.GameMode

data class GameModeSelectState (
    val gameModes: List<GameMode> = emptyList()
)