package com.hack.drinkingacademy.common.mappers

import com.hack.drinkingacademy.database.Game_element
import com.hack.drinkingacademy.game.domain.model.*

fun Game_element.toGameElement(gameMode: GameMode, gameCategory: GameCategory): GameElement {

    val gameElementType = GameElementType.fromLong(game_element_type_id)

    return GameElement(
        id = id,
        gameMode = gameMode,
        gameCategory = gameCategory,
        title = title,
        type = gameElementType,
        cards = content.toCards(gameElementType)
    )
}