package com.hack.drinkingacademy.game

import com.hack.drinkingacademy.game.model.GameCard
import kotlin.math.min

object GameCreationUtils {

    fun createGameFromCardsAndPlayers(
        cards: List<GameCard>,
        players: List<String>
    ): List<GameCard> {
        val playerCount = players.size
        val playerUsage = MutableList(playerCount) { 0 }
        val randomizedCards = cards.shuffled()

        return randomizedCards.map { card ->
            val placeholdersCount = "<player>".toRegex().findAll(card.description).count()
            val selectedPlayers = selectPlayers(players, playerUsage, placeholdersCount)
            val newDescription = replacePlaceholders(card.description, selectedPlayers)
            card.copy(description = newDescription)
        }
    }

    private fun selectPlayers(
        players: List<String>,
        playerUsage: MutableList<Int>,
        placeholdersCount: Int
    ): List<String> {
        val selectedPlayers = mutableSetOf<String>()

        while (selectedPlayers.size < placeholdersCount) {
            val playerIndex = (players.indices).shuffled().minByOrNull { playerUsage[it] }!!
            val player = players[playerIndex]

            if (!selectedPlayers.contains(player)) {
                selectedPlayers.add(player)
                playerUsage[playerIndex]++
            }
        }

        return selectedPlayers.toList().shuffled() // Shuffle to avoid predictability
    }

    private fun replacePlaceholders(description: String, players: List<String>): String {
        var updatedDescription = description
        players.forEach { player ->
            updatedDescription = replaceFirstOccurrence(updatedDescription, "<player>", player)
        }
        return updatedDescription
    }

    private fun replaceFirstOccurrence(source: String, target: String, replacement: String): String {
        val index = source.indexOf(target)
        return if (index == -1) source else source.substring(0, index) + replacement + source.substring(index + target.length)
    }

    fun computeGameSize(playersCount: Int) = min(playersCount * 5, 25)
}
