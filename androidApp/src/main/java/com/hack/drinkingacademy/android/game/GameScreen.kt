package com.hack.drinkingacademy.android.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hack.drinkingacademy.android.game.GameViewModel.GameState.Error
import com.hack.drinkingacademy.android.game.GameViewModel.GameState.Loading
import com.hack.drinkingacademy.android.game.GameViewModel.GameState.Running

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val gameState by viewModel.gameState.collectAsState()
    val difficulty = viewModel.gameDifficulty ?: 1

    when (gameState) {
        is Loading -> LoadingGameScreen()
        is Error -> ErrorGameScreen()
        is Running -> (gameState as Running).let {
            if (it.gameCards.isNotEmpty()) {
                RunningGameScreen(currentCard = it.gameCards.last(), onNextChallenge = viewModel::popCard, difficulty = difficulty)
            } else {
                FinishedGameScreen()
            }
        }
    }
}
