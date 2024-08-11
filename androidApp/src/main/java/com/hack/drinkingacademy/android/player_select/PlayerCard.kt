package com.hack.drinkingacademy.android.player_select

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hack.drinkingacademy.android.R

@Composable
fun PlayerCard(
    name: String,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
        .background(Color(0xAA000000)) // Match background with the game craziness level box
        .padding(8.dp)
        .clip(RoundedCornerShape(10.dp)) // Same rounded shape as the game craziness level box
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = name,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.rubik)),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        IconButton(onClick = { onCloseClick() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove player",
                tint = Color.Red // Red to make the close icon stand out
            )
        }
    }
}

@Composable
@Preview
fun PlayerCardPreview() {
    PlayerCard("Robert", {})
}