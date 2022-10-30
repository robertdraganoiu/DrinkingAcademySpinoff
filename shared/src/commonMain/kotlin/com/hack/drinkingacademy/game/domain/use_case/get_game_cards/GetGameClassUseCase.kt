package com.hack.drinkingacademy.game.domain.use_case.get_game_cards

import com.hack.drinkingacademy.game.domain.model.GameCard
import com.hack.drinkingacademy.game.domain.model.GameElement

class GetGameClassUseCase  {
    fun execute(gameElements: List<GameElement>): List<GameCard> {
        // TODO transform elements to cards algo
        return emptyList()
    }
}