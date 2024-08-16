package com.hack.drinkingacademy.game

import com.hack.drinkingacademy.game.model.GameCard
import com.hack.drinkingacademy.game.model.GameCategory
import com.hack.drinkingacademy.game.model.GameElement
import com.hack.drinkingacademy.game.model.GameMode

interface GameDataSource {

    suspend fun getRandomCards(difficulty: Int, count: Int): List<GameCard>
    suspend fun getGameModes(): List<GameMode>
    suspend fun getGameModeById(id: Long): GameMode?
    suspend fun getGameCategories(): List<GameCategory>
    suspend fun getGameCategoryById(id: Long): GameCategory?
    suspend fun getGameElementsByGameModeId(id: Long): List<GameElement>
}