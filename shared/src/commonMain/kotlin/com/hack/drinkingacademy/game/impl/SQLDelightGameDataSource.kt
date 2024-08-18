package com.hack.drinkingacademy.game.impl

import com.hack.drinkingacademy.common.mappers.toGameCard
import com.hack.drinkingacademy.database.GameDatabase
import com.hack.drinkingacademy.game.GameDataSource
import com.hack.drinkingacademy.game.model.ChallengeType
import com.hack.drinkingacademy.game.model.GameCard
import kotlin.math.max
import kotlin.math.min

class SQLDelightGameDataSource(db: GameDatabase) : GameDataSource {

    private val queries = db.gameQueries

    override suspend fun isMusicEnabled() = queries.getMusicEnabled().executeAsOne() == 1L

    override suspend fun toggleMusicEnabled() = queries.toggleMusicEnabled()

    override suspend fun getCardsWithDifficulty(difficulty: Long, n: Long): List<GameCard> {
        val mainCount = (n * 0.7).toLong()
        val belowCount = (n * 0.15).toLong()
        val aboveCount = n - mainCount - belowCount

        val mainDifficultyCards =
            queries.selectCardsWithDifficulty(difficulty, mainCount)
                .executeAsList()
        val belowDifficultyCards =
            queries.selectCardsWithDifficulty(max(difficulty - 1, 1), belowCount)
                .executeAsList()
        val aboveDifficultyCards =
            queries.selectCardsWithDifficulty(min(difficulty + 1, 5), aboveCount)
                .executeAsList()

        // Merge the lists and shuffle to ensure distribution across challenge types
        return (mainDifficultyCards + belowDifficultyCards + aboveDifficultyCards).shuffled()
            .map { it.toGameCard() }
    }

    override suspend fun getRandomCards(difficulty: Int, count: Int): List<GameCard> {
        return listOf(
            GameCard(ChallengeType.TRUTH, "<player> has to admit one of their sexual fantasies."),
            GameCard(ChallengeType.DARE, "<player> has undo <player>'s zipper with their mouth.")
        )
    }
}