package com.hack.drinkingacademy.android.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.game.model.ChallengeType
import com.hack.drinkingacademy.game.model.GameCard
import java.util.Locale

@Composable
fun RunningGameScreen(currentCard: GameCard, onNextChallenge: () -> Unit) {
    val backgroundColor by animateColorAsState(
        targetValue = when (currentCard.type) {
            ChallengeType.DARE -> Color.Red
            ChallengeType.TRUTH -> Color.Blue
            else -> Color.Gray
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .clickable { onNextChallenge() }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Challenge Type Text
            Text(
                text = currentCard.type.name.lowercase()
                    .replaceFirstChar { it.titlecase(Locale.ROOT) },
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
            )

            // Central Challenge Box with Tropical Theme
            AnimatedVisibility(visible = true) { // Change visibility for animation
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFF0E68C).copy(alpha = 0.85f), // Light khaki color to mimic parchment
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            border = BorderStroke(
                                4.dp,
                                Color(0xFF8B4513)
                            ), // Brown border for bamboo feel
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = currentCard.description[0],
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(Font(R.font.rubik)),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}