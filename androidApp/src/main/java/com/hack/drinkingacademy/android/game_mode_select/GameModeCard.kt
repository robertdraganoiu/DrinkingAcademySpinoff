package com.hack.drinkingacademy.android.game_mode_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.game.domain.model.GameMode

@Composable
fun GameModeCard(
    gameMode: GameMode,
    gameModeLogoId: Int,
    gameModeLogoDescriptionId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .background(Color.LightGray)
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Button(
            onClick = onClick,
            modifier = Modifier.clip(RoundedCornerShape(20.dp))
        ) {
            Image(
                painter = painterResource(gameModeLogoId),
                contentDescription = stringResource(gameModeLogoDescriptionId),
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = gameMode.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(end = 40.dp)
            )
        }
    }
}

@Composable
@Preview
fun GameModeCardPreview() {
    GameModeCard(
        GameMode(-1, "After hours ;)", "Sexy game", true),
        R.drawable.game_mode_logo_after_hours,
        R.string.game_mode_logo_after_hours_description,
        {})
}