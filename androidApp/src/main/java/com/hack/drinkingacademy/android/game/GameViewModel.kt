package com.hack.drinkingacademy.android.game

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hack.drinkingacademy.android.util.fromJson
import com.hack.drinkingacademy.game.GameCreationUtils
import com.hack.drinkingacademy.game.GameDataSource
import com.hack.drinkingacademy.game.model.GameCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    val gameDifficulty = savedStateHandle.get<Int>("difficulty")

    init {
        val players: List<String> = savedStateHandle.get<String>("players")?.fromJson() ?: emptyList()

        when {
            players.isNullOrEmpty() -> {
                val errorMessage = "Players list is $players Aborting cards loading."
                Log.e(GameViewModel::class.simpleName, errorMessage)
                _gameState.value =
                    GameState.Error(errorMessage)
            }
            gameDifficulty == null || gameDifficulty.toInt() !in 1..5 -> {
                val errorMessage = "Difficulty setting is $gameDifficulty. Aborting cards loading."
                Log.e(GameViewModel::class.simpleName, errorMessage)
                _gameState.value =
                    GameState.Error(errorMessage)
            }
            else -> loadGameCards(players, gameDifficulty)
        }
    }

    private fun loadGameCards(players: List<String>, difficulty: Int) {
        Log.d("GameViewModel", "$players, $difficulty")
        viewModelScope.launch {
            _gameState.value = GameState.Loading

            // Keep this for now to show loading screen
            delay(1000)

            try {
                val gameCards = GameCreationUtils.createGameFromCardsAndPlayers(
                    gameDataSource.getCardsWithDifficulty(
                        difficulty.toLong(),
                        GameCreationUtils.computeGameSize(players.size).toLong()
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

    sealed class GameState {
        object Loading : GameState()
        data class Running(val gameCards: List<GameCard>) : GameState()
        data class Error(val message: String) : GameState()
    }
}
