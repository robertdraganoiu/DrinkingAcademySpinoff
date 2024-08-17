package com.hack.drinkingacademy.game

import com.hack.drinkingacademy.game.model.GameCard
import kotlin.math.min

object GameCreationUtils {

    fun createGameFromCardsAndPlayers(
        cards: List<GameCard>,
        players: List<String>
    ): List<GameCard> {
        return cards
//        val playerPlaceholder = "<player>"
//        val randomGenerator = RandomGenerator()
//
//        // List to hold the final game cards with player names replaced
//        val updatedCards = mutableListOf<GameCard>()
//
//        for (card in cards) {
//            // Create a list of players to choose from and shuffle it
//            val shuffledPlayers = players.shuffled(randomGenerator)
//            val playersToUse = shuffledPlayers.toMutableList()
//
//            // List to store the updated description lines
//            val updatedDescriptionLines = mutableListOf<String>()
//
//            // Process each line in the card's description
//            card.description.forEach { descriptionLine ->
//                var line = descriptionLine
//                val usedPlayers = mutableSetOf<String>()
//
//                // Ensure unique players in the same line
//                while (line.contains(playerPlaceholder)) {
//                    // Filter players who haven't been used yet in this card
//                    val availablePlayers = playersToUse.filterNot { usedPlayers.contains(it) }
//                    if (availablePlayers.isEmpty()) break
//
//                    // Select a random player from the available players
//                    val playerName = availablePlayers[randomGenerator.nextInt(availablePlayers.size)]
//
//                    // Replace the first occurrence of <player> in the line
//                    line = replaceFirstOccurrence(line, playerPlaceholder, playerName)
//
//                    // Mark this player as used in this card
//                    usedPlayers.add(playerName)
//                }
//                updatedDescriptionLines.add(line)
//            }
//
//            // Create a new GameCard with the updated description
//            val updatedCard = card.copy(description = updatedDescriptionLines)
//            updatedCards.add(updatedCard)
//        }
//
//        return updatedCards
    }

    fun computeGameSize(playersCount: Int) = min(playersCount * 5, 25)

    private fun replaceFirstOccurrence(source: String, target: String, replacement: String): String {
        val index = source.indexOf(target)
        return if (index == -1) source else source.substring(0, index) + replacement + source.substring(index + target.length)
    }
}