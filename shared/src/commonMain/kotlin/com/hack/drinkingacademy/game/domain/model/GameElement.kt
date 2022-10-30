package com.hack.drinkingacademy.game.domain.model

data class GameElement(
    val id: Long,
    val gameMode: GameMode,
    val gameCategory: GameCategory,
    val title: String,
    val type: GameElementType,
    val cards: List<GameCard>
)