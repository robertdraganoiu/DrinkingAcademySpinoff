package com.hack.drinkingacademy.game.domain.repository

import com.hack.drinkingacademy.game.domain.model.GameCategory
import com.hack.drinkingacademy.game.domain.model.GameElement
import com.hack.drinkingacademy.game.domain.model.GameMode

interface GameDataSource {
    suspend fun getGameModes(): List<GameMode>
    suspend fun getGameModeById(id: Long): GameMode?
    suspend fun getGameCategories(): List<GameCategory>
    suspend fun getGameCategoryById(id: Long): GameCategory?
    suspend fun getGameElementsByGameModeId(id: Long): List<GameElement>
}