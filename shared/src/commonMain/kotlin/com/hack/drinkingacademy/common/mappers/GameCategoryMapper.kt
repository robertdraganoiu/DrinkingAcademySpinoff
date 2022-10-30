package com.hack.drinkingacademy.common.mappers

import com.hack.drinkingacademy.database.Game_category
import com.hack.drinkingacademy.game.domain.model.GameCategory

fun Game_category.toGameCategory(): GameCategory {
    return GameCategory(
        id = id,
        name = name,
        description = description
    )
}