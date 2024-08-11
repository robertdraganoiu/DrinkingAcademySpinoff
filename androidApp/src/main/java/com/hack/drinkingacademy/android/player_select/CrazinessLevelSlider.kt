package com.hack.drinkingacademy.android.player_select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hack.drinkingacademy.android.R

@Composable
fun CrazinessLevelSlider(crazinessLevel: Float, onValueChange: (Float) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 16.dp)
            .background(Color(0xAA000000)) // Semi-transparent dark background
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Text(
            text = "Choose Game Craziness Level",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = FontFamily(Font(R.font.rubik)),
            color = Color.White // Brighter text for readability
        )
        Slider(
            value = crazinessLevel,
            onValueChange = onValueChange,
            valueRange = 1f..5f,
            steps = 3,
            colors = SliderDefaults.colors(
                thumbColor = Color.Yellow, // High contrast color
                activeTrackColor = Color.Cyan // Complements the background
            ),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = crazinessLevel.toInt().toCrazinessLevelString(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.rubik)),
            color = Color.White
        )
    }
}

fun Int.toCrazinessLevelString(): String = when (this) {
    1 -> "1 - Sunny"
    2 -> "2 - Breezy"
    3 -> "3 - Tropical"
    4 -> "4 - Wild"
    else -> "5 - Hurricane"
}