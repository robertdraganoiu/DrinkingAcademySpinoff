package com.hack.drinkingacademy.android.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.hack.drinkingacademy.android.R

@Composable
fun FinishedGameScreen(navController: NavHostController, difficulty: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navController.popBackStack() }
    ) {
        // Background Image
        Image(
            painter = painterResource(difficulty.toGameBackground()),
            contentDescription = stringResource(id = R.string.background_game_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .zIndex(-1f)
        )

        // Tint Overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.25f))
                .zIndex(0f)
        )

        // Centered content
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Title Text
            Text(
                text = "Game Over :)",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

            // ChallengeCard
            ChallengeCard(description = "Tap the screen to return to main menu.")
        }
    }
}

