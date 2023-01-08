package com.hack.drinkingacademy.android.game_mode_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hack.drinkingacademy.android.R

@Composable
fun GameModeSelectScreen(
    viewModel: GameModeSelectViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val gameModes by viewModel.gameModes.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            gameModes.forEach { gameMode ->
                GameModeCard(
                    gameMode = gameMode,
                    gameModeLogoId = viewModel.getGameModeLogoDrawableIdFromId(gameMode.id),
                    gameModeLogoDescriptionId = viewModel.getGameModeLogoDescriptionIdFromId(
                        gameMode.id
                    ),
                    onClick = {
                        val selected = viewModel.selectGameMode(gameMode.id.toInt() - 1)
                        if (selected) navController.navigate("game")
                    })
            }
        }

        // background image
        Image(
            painter = painterResource(id = R.drawable.background_game_mode_select),
            contentDescription = stringResource(id = R.string.background_game_mode_select_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .zIndex(-1f)
        )
    }
}