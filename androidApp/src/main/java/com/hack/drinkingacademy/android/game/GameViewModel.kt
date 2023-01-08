package com.hack.drinkingacademy.android.game

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hack.drinkingacademy.game.domain.model.GameCard
import com.hack.drinkingacademy.game.domain.repository.GameDataSource
import com.hack.drinkingacademy.game.domain.use_case.get_game_cards.TransformGameElementsToCardsUseCase
import com.hack.drinkingacademy.game.domain.use_case.select_game_elements.FilterGameElementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameDataSource: GameDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val transformElementsToCards = TransformGameElementsToCardsUseCase()
    private val filterElements = FilterGameElementsUseCase()

    private val players = savedStateHandle.getStateFlow("players", emptyList<String>())
    private val gameModeId = savedStateHandle.getStateFlow("gameMode", -1)
    private val cards = savedStateHandle.getStateFlow("cards", emptyList<GameCard>())
    private val currentCard = savedStateHandle.getStateFlow("currentCard", 0)

    init {
        loadGameCards(players.value, gameModeId.value)
    }

    private fun loadGameCards(players: List<String>, gameModeId: Int) {
        if (players.isEmpty()) {
            Log.e(
                GameViewModel::class.simpleName,
                "No players are were found! Aborting cards loading."
            )
            return
        }
        if (gameModeId == -1) {
            Log.e(
                GameViewModel::class.simpleName,
                "Game mode is not selected! Aborting cards loading."
            )
            return
        }

        viewModelScope.launch {
            val allGameModeElements = gameDataSource.getGameElementsByGameModeId(gameModeId.toLong())
            val gameCards =
                transformElementsToCards.execute(
                    filterElements.execute(
                        allGameModeElements,
                        players
                    )
                )
            savedStateHandle["cards"] = gameCards
            Log.i(
                GameViewModel::class.simpleName,
                "${gameCards.size} game cards were successfully created."
            )
        }
    }

    private fun getCurrentCard(): GameCard? =
        if (currentCard.value < cards.value.size) cards.value[currentCard.value] else null

    private fun incrementCurrentCard(): Unit {
        savedStateHandle["currentCard"] = currentCard.value + 1
    }
}