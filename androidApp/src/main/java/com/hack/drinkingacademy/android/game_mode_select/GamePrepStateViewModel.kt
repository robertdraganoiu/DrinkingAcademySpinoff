package com.hack.drinkingacademy.android.game_mode_select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hack.drinkingacademy.game.domain.model.GameMode
import com.hack.drinkingacademy.game.domain.repository.GameDataSource
import com.hack.drinkingacademy.game.domain.use_case.get_game_cards.TransformGameElementsToCardsUseCase
import com.hack.drinkingacademy.game.domain.use_case.select_game_elements.FilterGameElementsUseCase
import com.hack.drinkingacademy.user.domain.repository.UserDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class GamePrepStateViewModel @Inject constructor(
    private val userDataSource: UserDataSource,
    private val gameDataSource: GameDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val transformElementsToCards =  TransformGameElementsToCardsUseCase()
    private val filterElements = FilterGameElementsUseCase()

    val user = savedStateHandle.getStateFlow("user", null)
    val players = savedStateHandle.getStateFlow("players", emptyList<String>())
    val gameModes = savedStateHandle.getStateFlow("gameModes", emptyList<GameMode>())

    val state = combine(user, players, gameModes) { user, players, gameModes ->
        GamePrepState(
            user = user,
            players = players,
        )
    }
}