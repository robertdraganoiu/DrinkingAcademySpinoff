package com.hack.drinkingacademy.android.game

import com.hack.drinkingacademy.game.domain.model.GameCard
import com.hack.drinkingacademy.game.domain.model.GameElement
import com.hack.drinkingacademy.game.domain.model.GameMode

data class GameState (
    val players: List<String> = emptyList(),
    val gameMode: GameMode? = null,
    val cards: List<GameCard> = emptyList()
)
