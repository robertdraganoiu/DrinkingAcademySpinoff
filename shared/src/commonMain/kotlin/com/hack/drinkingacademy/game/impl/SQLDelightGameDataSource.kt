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
            ChallengeType.MASTER to 1L, // 1 master
            ChallengeType.BETRAYAL to 1L, // 1 betrayal
            ChallengeType.DARE to (n * 0.35).toLong(), // 35% dares
            ChallengeType.TRUTH to (n * 0.2).toLong(), // 20% truths
            ChallengeType.POLL to (n * 0.15).toLong(), // 15% polls
            ChallengeType.SECRET_POLL to (n * 0.15).toLong(), // 15% polls
            ChallengeType.CATEGORIES to (n * 0.15).toLong(), // 15% categories
        )

        val cards = mutableListOf<List<GameCard>>()

        // Fetch cards for each challenge type based on the defined distribution
        distribution.forEach { (type, count) ->
            val randomizedDifficulty = getRandomizedDifficulty(difficulty)
            val fetchedCards =
                queries.selectCardsWithDifficultyAndType(randomizedDifficulty, type.name, count)
                    .executeAsList().map {
                        if (type == ChallengeType.SECRET_POLL) {
                            listOf(
                                GameCard(
                                    type = type,
                                    description = "Everyone but <player> closes their eyes. This person reads question on the next screen."
                                ),
                                GameCard(
                                    type = type,
                                    description = it.content + "\n\nVote at the same time, thumb up for yes, thumb down for no. Everyone keeps their eyes closed and votes casted.\n\nTap to see if the poll remains a secret."
                                ),
                                GameCard(
                                    type = type, description = if (getRandomizedSecretPoll()) {
                                        "The poll is revealed.\n\nKeep your votes casted and open your eyes.\n\nThe minority drinks ${getRandomizedSips()} sips."
                                    } else {
                                        "The poll is NOT revealed.\n\nFirst put your votes down, then open your eyes."
                                    }
                                ),
                            ).reversed()// reversed for flatten for some reason

                        } else {
                            listOf(
                                GameCard(
                                    type = type, description = when (type) {
                                        ChallengeType.POLL -> it.content + "\n\nVote at the same time, thumb up for yes, thumb down for no.\n\nThe minority drinks ${getRandomizedSips()} sips."
                                        ChallengeType.CATEGORIES -> it.content + ".\n\nPick an order between yourselves. Everyone says one until something is repeated or someone runs out of things to say.\n\nThe loser drinks ${getRandomizedSips()} sips."
                                        else -> it.content
                                    }
                                )
                            )
                        }
                    }

            cards.addAll(fetchedCards)
        }

        // Shuffle the combined list to ensure random order
        return cards.shuffled().flatten()
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

    private fun getRandomizedSips(): Long {
        return when ((1..100).random()) {
            in 1..25 -> 2
            in 26..75 -> 3
            else -> 4
        }
    }

    private fun getRandomizedSecretPoll(): Boolean {
        return when ((1..100).random()) {
            in 1..50 -> false
            else -> true
        }
    }
}