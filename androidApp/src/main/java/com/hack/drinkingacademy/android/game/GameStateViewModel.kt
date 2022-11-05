package com.hack.drinkingacademy.android.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hack.drinkingacademy.game.domain.model.GameElement
import com.hack.drinkingacademy.game.domain.repository.GameDataSource
import com.hack.drinkingacademy.game.domain.use_case.get_game_cards.TransformGameElementsToCardsUseCase
import com.hack.drinkingacademy.game.domain.use_case.select_game_elements.FilterGameElementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameStateViewModel @Inject constructor(
    private val gameDataSource: GameDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val transformElementsToCards =  TransformGameElementsToCardsUseCase()
    private val filterElements = FilterGameElementsUseCase()

    val players = savedStateHandle.getStateFlow("players", emptyList<String>())
    val gameMode = savedStateHandle.getStateFlow("gameMode", null)
    val cards = savedStateHandle.getStateFlow("cards", emptyList<GameElement>())

//    val state = GameState(
//
//    )
}