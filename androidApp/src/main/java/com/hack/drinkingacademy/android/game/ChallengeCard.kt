package com.hack.drinkingacademy.android.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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