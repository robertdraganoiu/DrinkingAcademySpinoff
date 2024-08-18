package com.hack.drinkingacademy.android.player_select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerSelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val players = savedStateHandle.getStateFlow("players", emptyList<String>())
    val difficulty = savedStateHandle.getStateFlow("difficulty", 3)

    fun addPlayer(name: String) {
        savedStateHandle["players"] = players.value + name
    }

    fun removePlayer(name: String) {
        savedStateHandle["players"] = players.value.toMutableList().filter { it != name }
    }

    fun setDifficulty(difficulty: Int) {
        savedStateHandle["difficulty"] = difficulty
    }
}