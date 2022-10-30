package com.hack.drinkingacademy.dbloader.data.repository

import com.hack.drinkingacademy.common.mappers.toGameCategory
import com.hack.drinkingacademy.common.mappers.toGameMode
import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.dbloader.domain.model.GameCategoryDetails
import com.hack.drinkingacademy.dbloader.domain.model.GameElementDetails
import com.hack.drinkingacademy.dbloader.domain.model.GameModeDetails
import com.hack.drinkingacademy.dbloader.domain.repository.GameDataLoader
import com.hack.drinkingacademy.game.domain.model.GameElementType

class SQLDelightGameDataLoader(db: GameDatabase) : GameDataLoader {

    private val queries = db.gameQueries

    override suspend fun addGameMode(gameModeDetails: GameModeDetails) {
        queries.addGameMode(gameModeDetails.name, gameModeDetails.description)
    }

    override suspend fun addGameCategory(gameCategoryDetails: GameCategoryDetails) {
        queries.addGameCategory(gameCategoryDetails.name, gameCategoryDetails.description)
    }

    override suspend fun addGameElement(gameElementDetails: GameElementDetails) {
        val gameModeId = queries.getGameModeByName(gameElementDetails.gameModeName)
            .executeAsOneOrNull()
            ?.toGameMode()
            ?.id

        val gameCategoryId = queries.getGameCategoryByName(gameElementDetails.gameCategoryName)
            .executeAsOneOrNull()
            ?.toGameCategory()
            ?.id

        if (gameModeId == null || gameCategoryId == null)
            throw NoSuchElementException("Wrong game mode name or game category name!")

        // throws NoSuchElementException if name is wrong
        val gameElementTypeId = GameElementType.fromName(gameElementDetails.type).id

        queries.addGameElement(
            gameModeId,
            gameCategoryId,
            gameElementTypeId,
            gameElementDetails.title,
            gameElementDetails.content
        )
    }
}