package com.hack.drinkingacademy.android.game

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hack.drinkingacademy.game.GameCreationUtils
import com.hack.drinkingacademy.game.GameDataSource
import com.hack.drinkingacademy.game.model.GameCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gameDataSource: GameDataSource,
) : ViewModel() {

    private val _gameState = MutableStateFlow<GameState>(GameState.Loading)
    val gameState: StateFlow<GameState> = _gameState

    init {
        loadGameCards(
            players = savedStateHandle["players"],
            difficulty = savedStateHandle["difficulty"]
        )
    }

    private fun loadGameCards(players: List<String>?, difficulty: Int?) {
        if (players.isNullOrEmpty()) {
            val errorMessage = "Players list is $players Aborting cards loading."
            Log.e(GameViewModel::class.simpleName, errorMessage)
            _gameState.value =
                GameState.Error(errorMessage)
            return
        }
        if (difficulty == null || difficulty !in 1..5) {
            val errorMessage = "Difficulty setting is $difficulty. Aborting cards loading."
            Log.e(GameViewModel::class.simpleName, errorMessage)
            _gameState.value =
                GameState.Error(errorMessage)
            return
        }

        viewModelScope.launch {
            _gameState.value = GameState.Loading

            try {
                val gameCards = GameCreationUtils.createGameFromCardsAndPlayers(
                    gameDataSource.getRandomCards(
                        difficulty,
                        GameCreationUtils.computeGameSize(players.size)
                    ), players
                )
                _gameState.value = GameState.Running(gameCards.toMutableList())
                Log.i(
                    GameViewModel::class.simpleName,
                    "${gameCards.size} game cards were successfully created."
                )
            } catch (e: Exception) {
                _gameState.value = GameState.Error("Failed to load game cards: ${e.message}")
                Log.e(GameViewModel::class.simpleName, "Error loading game cards: ${e.message}")
            }
        }
    }

    fun popCard() {
        val currentState = _gameState.value
        if (currentState is GameState.Running) {
            val currentStack = currentState.gameCards.toMutableList()
            if (currentStack.isNotEmpty()) {
                currentStack.removeAt(currentStack.lastIndex)
                _gameState.value = GameState.Running(currentStack)
            }
        }
    }

    fun isGameOver(): Boolean {
        return (gameState.value as? GameState.Running)?.gameCards?.isEmpty() ?: true
    }

    sealed class GameState {
        object Loading : GameState()
        data class Running(val gameCards: List<GameCard>) : GameState()
        data class Error(val message: String) : GameState()
    }
}
