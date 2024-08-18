package com.hack.drinkingacademy.game.impl

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
        // Define the distribution of challenge types
        val distribution = mapOf(
            ChallengeType.MASTER to (n * 0.1).toLong(), // 10% master
            ChallengeType.BETRAYAL to (n * 0.05).toLong(), // 5% betrayals
            ChallengeType.DARE to (n * 0.4).toLong(), // 40% dares
            ChallengeType.TRUTH to (n * 0.15).toLong(), // 15% truths
            ChallengeType.POLL to (n * 0.2).toLong(), // 20% polls
            ChallengeType.TRIVIA to (n * 0.1).toLong(), // 10% trivia
        )

        val cards = mutableListOf<GameCard>()

        // Fetch cards for each challenge type based on the defined distribution
        distribution.forEach { (type, count) ->
            val randomizedDifficulty = getRandomizedDifficulty(difficulty)
            val fetchedCards =
                queries.selectCardsWithDifficultyAndType(randomizedDifficulty, type.name, count)
                    .executeAsList()
                    .map {
                        GameCard(
                            type = type,
                            description = it.content
                        )
                    }

            cards.addAll(fetchedCards)
        }

        // Shuffle the combined list to ensure random order
        return cards.shuffled()
    }

    override suspend fun getRandomCards(difficulty: Int, count: Int): List<GameCard> {
        return listOf(
            GameCard(ChallengeType.TRUTH, "<player> has to admit one of their sexual fantasies."),
            GameCard(ChallengeType.DARE, "<player> has undo <player>'s zipper with their mouth.")
        )
    }

    private fun getRandomizedDifficulty(baseDifficulty: Long): Long {
        return when ((1..100).random()) {
            in 1..15 -> max(baseDifficulty - 1, 1) // 15% chance to get one below
            in 16..30 -> min(baseDifficulty + 1, 5) // 15% chance to get one above
            else -> baseDifficulty // 70% chance to get the actual difficulty
        }
    }
}