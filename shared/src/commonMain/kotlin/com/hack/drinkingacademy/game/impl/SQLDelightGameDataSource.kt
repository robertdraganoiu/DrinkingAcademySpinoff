package com.hack.drinkingacademy.game.impl

import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.game.GameDataSource
import com.hack.drinkingacademy.game.model.ChallengeType
import com.hack.drinkingacademy.game.model.GameCard

class SQLDelightGameDataSource(db: GameDatabase) : GameDataSource {

    private val queries = db.gameQueries

    override suspend fun getRandomCards(difficulty: Int, count: Int): List<GameCard> {
        return listOf(
            GameCard(ChallengeType.TRUTH, "<player> has to admit one of their sexual fantasies."),
            GameCard(ChallengeType.DARE, "<player> has undo <player>'s zipper with their mouth.")
        )
    }

//    override suspend fun getGameModes(): List<GameMode> {
//        return queries.getGameModes().executeAsList().map { it.toGameMode() }
//    }
//
//    override suspend fun getGameModeById(id: Long): GameMode? {
//        return queries.getGameModeById(id)
//            .executeAsOneOrNull()
//            ?.toGameMode()
//    }
//
//    override suspend fun getGameCategories(): List<GameCategory> {
//        return queries.getGameCategories().executeAsList().map { it.toGameCategory() }
//    }
//
//    override suspend fun getGameCategoryById(id: Long): GameCategory? {
//        return queries.getGameCategoryById(id)
//            .executeAsOneOrNull()
//            ?.toGameCategory()
//    }
//
//    override suspend fun getGameElementsByGameModeId(id: Long): List<GameElement> {
//        return queries.getGameElementsByGameModeId(id)
//            .executeAsList()
//            .map {
//                it.toGameElement(
//                    getGameModeById(it.game_mode_id)!!,
//                    getGameCategoryById(it.game_category_id)!!
//                )
//            }
//    }
}