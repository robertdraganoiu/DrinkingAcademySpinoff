package com.hack.drinkingacademy.game

import com.hack.drinkingacademy.game.model.GameCard

interface GameDataSource {

    suspend fun getRandomCards(difficulty: Int, count: Int): List<GameCard>
}