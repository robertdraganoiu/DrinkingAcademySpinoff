package com.hack.drinkingacademy.android.player_select

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hack.drinkingacademy.common.constants.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerSelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val players = savedStateHandle.getStateFlow("players", emptyList<String>())
    val difficulty = savedStateHandle.getStateFlow("difficulty", 1f)

    fun addPlayer(name: String) {
        if (players.value.size == Constants.MAX_PLAYERS) {
            Log.i(
                PlayerSelectViewModel::class.simpleName,
                "Tried to add more than ${Constants.MAX_PLAYERS} players. Aborted adding."
            )
            return
        }
        savedStateHandle["players"] = players.value + name
    }

    fun removePlayer(name: String) {
        savedStateHandle["players"] = players.value.toMutableList().filter { it != name }
    }

    fun setDifficulty(difficulty: Int) {
        savedStateHandle["difficulty"] = difficulty
    }
}