package com.hack.drinkingacademy.android.player_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.hack.drinkingacademy.android.R
import com.hack.drinkingacademy.android.player_select.PlayerSelectViewModel
import java.util.*

@Composable
fun PlayerSelectScreen(
    viewModel: PlayerSelectViewModel = hiltViewModel()
) {
    val players by viewModel.players.collectAsState()

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
            players.forEach { name ->
                PlayerCard(name = name, onCloseClick = { viewModel.removePlayer(name) })
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Button(
                    onClick = { viewModel.addPlayer("Robert ${Random().nextInt(20)}") }
                ) {
                    Text(
                        text = "Add player",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                }
                Button(
                    onClick = { /* TODO navigation */}
                ) {
                    Text(
                        text = "Next",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                }
            }
        }

        // background image
        Image(
            painter = painterResource(id = R.drawable.player_select_screen_background),
            contentDescription = stringResource(id = R.string.player_select_background_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .zIndex(-1f)
        )
    }
}