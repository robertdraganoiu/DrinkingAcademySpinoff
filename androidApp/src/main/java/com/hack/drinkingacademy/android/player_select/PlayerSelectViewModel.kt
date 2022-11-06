package com.hack.drinkingacademy.android.player_select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hack.drinkingacademy.common.constants.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class PlayerSelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val players = savedStateHandle.getStateFlow("players", emptyList<String>())

    fun addPlayer(name: String) {
        if (players.value.size == Constants.MAX_PLAYERS) return
        savedStateHandle["players"] = players.value + name
    }

    fun removePlayer(name: String) {
        savedStateHandle["players"] = players.value.toMutableList().filter { it != name}
    }
}