package com.hack.drinkingacademy.android.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
        Image(
            painter = painterResource(difficulty.toGameBackground()),
            contentDescription = stringResource(id = R.string.background_player_select_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .zIndex(-1f)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(tintColor)
                .zIndex(0f)
        )

        Column(
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

            Spacer(modifier = Modifier.weight(1f)) // Take up the remaining space

            // Scrollable Challenge Description
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

            Spacer(modifier = Modifier.weight(1f)) // Take up the remaining space
        }
    }
}

@Composable
fun ChallengeCard(description: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color(0xAA000000), // Semi-transparent dark background
                shape = CutCornerShape(6.dp) // Matching the slider's corner radius
            )
            .padding(16.dp)
    ) {
        Text(
            text = description,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.rubik)),
            modifier = Modifier.align(Alignment.Center)
        )
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
    ChallengeType.DARE -> Color.Red.copy(alpha = 0.15f)
    ChallengeType.TRUTH -> Color.Blue.copy(alpha = 0.15f)
    ChallengeType.POLL -> Color.Green.copy(alpha = 0.15f)
    ChallengeType.MASTER -> Color.Yellow.copy(alpha = 0.15f)
    ChallengeType.TRIVIA -> Color.Magenta.copy(alpha = 0.15f)
    ChallengeType.BETRAYAL -> Color.Red.copy(alpha = 0.3f)
}
