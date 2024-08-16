package com.hack.drinkingacademy.android.game_mode_select

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.game.model.GameMode
import com.hack.drinkingacademy.game.GameDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameModeSelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val gameDataSource: GameDataSource
) : ViewModel() {
    val gameModes = savedStateHandle.getStateFlow("gameModes", emptyList<GameMode>())

    init {
        loadGameModes()
    }

    private fun loadGameModes() {
        viewModelScope.launch {
            val gameModes = gameDataSource.getGameModes()
            savedStateHandle["gameModes"] = gameModes
            Log.i(GameModeSelectViewModel::class.simpleName, "Loaded ${gameModes.size} game modes.")
        }
    }

    fun getGameModeLogoDrawableIdFromId(id: Long): Int = when (id.toInt()) {
        0 -> R.drawable.game_mode_logo_inauguration
        1 -> R.drawable.game_mode_logo_gender_olympics
        2 -> R.drawable.game_mode_logo_after_hours
        3 -> R.drawable.game_mode_logo_survival
        4 -> R.drawable.game_mode_logo_team_building
        else -> R.drawable.game_mode_logo_inauguration
    }

    fun getGameModeLogoDescriptionIdFromId(id: Long): Int = when (id.toInt()) {
        0 -> R.string.game_mode_logo_inauguration_description
        1 -> R.string.game_mode_logo_gender_olympics_description
        2 -> R.string.game_mode_logo_after_hours_description
        3 -> R.string.game_mode_logo_survival_description
        4 -> R.string.game_mode_logo_team_building_description
        else -> R.string.game_mode_logo_inauguration_description
    }

    fun selectGameMode(id: Int): Boolean {
        val gameModesList = gameModes.value
        if (id < 0 || id >= gameModesList.size) {
            Log.e(
                GameModeSelectViewModel::class.simpleName,
                "Id $id out of bounds. Max game mode list has ${gameModesList.size} elements. Aborted selection."
            )
            return false
        }
        if (!gameModesList[id].isEnabled) {
            Log.i(
                GameModeSelectViewModel::class.simpleName,
                "Tried to select a game mode that is not enabled. Aborted selection."
            )
            return false
        }
        savedStateHandle["gameMode"] = gameModesList[id].id
        Log.i(
            GameModeSelectViewModel::class.simpleName,
            "Game mode $id(${gameModesList[id].name} selected."
        )
        return true
    }
}