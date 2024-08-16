package com.hack.drinkingacademy.game

import com.hack.drinkingacademy.game.model.GameCard
import kotlin.math.min

object GameCreationUtils {
    fun createGameFromCardsAndPlayers(
        cards: List<GameCard>,
        players: List<String>
    ): List<GameCard> {
        return cards
    }

    fun computeGameSize(playersCount: Int) = min(playersCount * 4, 20)
}