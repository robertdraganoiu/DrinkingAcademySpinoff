package com.hack.drinkingacademy.android.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.game.model.ChallengeType
import com.hack.drinkingacademy.game.model.GameCard
import java.util.Locale

@Composable
fun RunningGameScreen(currentCard: GameCard, onNextChallenge: () -> Unit, difficulty: Int) {
    val tintColor by animateColorAsState(
        targetValue = currentCard.type.toColorTint()
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onNextChallenge() }
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
                .background(tintColor)
                .zIndex(0f)
        )

        // Centered Content
        Column(
            verticalArrangement = Arrangement.Center,
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
                    .padding(bottom = 16.dp) // Add some space below the title
            )

            // Challenge Description Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier.align(Alignment.Center) // Center the LazyColumn
                ) {
                    items(currentCard.description) { desc ->
                        ChallengeCard(description = desc)
                    }
                }
            }
        }
    }
}

fun Int.toGameBackground() = when (this) {
    1 -> R.drawable.background_difficulty_1
    2 -> R.drawable.background_difficulty_2
    3 -> R.drawable.background_difficulty_3
    4 -> R.drawable.background_difficulty_4
    else -> R.drawable.background_difficulty_5
}

fun ChallengeType.toColorTint() = when (this) {
    ChallengeType.DARE -> Color.Red.copy(alpha = 0.25f)
    ChallengeType.TRUTH -> Color.Blue.copy(alpha = 0.25f)
    ChallengeType.POLL -> Color.Green.copy(alpha = 0.25f)
    ChallengeType.MASTER -> Color.Yellow.copy(alpha = 0.25f)
    ChallengeType.TRIVIA -> Color.Magenta.copy(alpha = 0.25f)
    ChallengeType.BETRAYAL -> Color.Red.copy(alpha = 0.4f)
}
