package com.hack.drinkingacademy.common.mappers

import com.hack.drinkingacademy.database.Game_mode
import com.hack.drinkingacademy.game.domain.model.GameMode

fun Game_mode.toGameMode(): GameMode {
    return GameMode(
        id = id,
        name = name,
        description = description,
        isEnabled = isEnabled != 0L
    )
}