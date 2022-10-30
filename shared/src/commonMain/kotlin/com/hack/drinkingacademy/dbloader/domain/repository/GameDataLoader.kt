package com.hack.drinkingacademy.dbloader.domain.repository

import com.hack.drinkingacademy.dbloader.domain.model.GameCategoryDetails
import com.hack.drinkingacademy.dbloader.domain.model.GameElementDetails
import com.hack.drinkingacademy.dbloader.domain.model.GameModeDetails
import com.hack.drinkingacademy.game.domain.model.GameCategory
import com.hack.drinkingacademy.game.domain.model.GameElement
import com.hack.drinkingacademy.game.domain.model.GameMode

interface GameDataLoader {
    suspend fun addGameMode(gameModeDetails: GameModeDetails)
    suspend fun addGameCategory(gameCategoryDetails: GameCategoryDetails)
    suspend fun addGameElement(gameElementDetails: GameElementDetails)
}