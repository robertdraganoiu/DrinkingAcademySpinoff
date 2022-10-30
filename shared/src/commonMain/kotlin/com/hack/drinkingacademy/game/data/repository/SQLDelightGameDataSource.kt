package com.hack.drinkingacademy.game.data.repository

import com.hack.drinkingacademy.common.mappers.toGameCategory
import com.hack.drinkingacademy.common.mappers.toGameElement
import com.hack.drinkingacademy.common.mappers.toGameMode
import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.game.domain.model.GameCategory
import com.hack.drinkingacademy.game.domain.model.GameElement
import com.hack.drinkingacademy.game.domain.model.GameMode
import com.hack.drinkingacademy.game.domain.repository.GameDataSource

class SQLDelightGameDataSource(db: GameDatabase) : GameDataSource {

    private val queries = db.gameQueries

    override suspend fun getGameModes(): List<GameMode> {
        return queries.getGameModes().executeAsList().map {it.toGameMode()}
    }

    override suspend fun getGameModeById(id: Long): GameMode? {
        return queries.getGameModeById(id)
            .executeAsOneOrNull()
            ?.toGameMode()
    }

    override suspend fun getGameCategories(): List<GameCategory> {
        return queries.getGameCategories().executeAsList().map { it.toGameCategory() }
    }

    override suspend fun getGameCategoryById(id: Long): GameCategory? {
        return queries.getGameCategoryById(id)
            .executeAsOneOrNull()
            ?.toGameCategory()
    }

    override suspend fun getGameElementsByGameModeId(id: Long): List<GameElement> {
        return queries.getGameElementsByGameModeId(id)
            .executeAsList()
            .map { it.toGameElement(
                getGameModeById(it.game_mode_id)!!,
                getGameCategoryById(it.game_category_id)!!)}
    }
}