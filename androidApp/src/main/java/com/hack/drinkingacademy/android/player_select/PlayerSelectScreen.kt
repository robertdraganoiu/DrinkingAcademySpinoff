package com.hack.drinkingacademy.android.player_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hack.drinkingacademy.android.R

@Composable
fun PlayerSelectScreen(
    viewModel: PlayerSelectViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val players by viewModel.players.collectAsState()
    val crazinessLevel by viewModel.difficulty.collectAsState()
    var showPlayerInputField by remember { mutableStateOf(false) }
    var inputPlayerName by remember { mutableStateOf("") }

    // Background Box with black overlay
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.4f)) // Optional darker overlay
    ) {
        // Main Column Layout
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Game Title
            Text(
                text = "Dare & Down",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.press_start_2p)),
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Craziness Level Slider Section
            CrazinessLevelSlider(crazinessLevel) { newValue ->
                viewModel.setDifficulty(newValue.toInt())
            }

            // "Players" Header
            Text(
                text = "Players",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.press_start_2p)),
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // Player List with + Button and Input Field
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(players.size) { index ->
                    PlayerCard(
                        name = players[index],
                        onCloseClick = { viewModel.removePlayer(players[index]) }
                    )
                }

                // Add Player Button and TextField
                item {
                    if (showPlayerInputField) {
                        PlayerCard(
                            name = inputPlayerName,
                            onCloseClick = {
                                showPlayerInputField = false
                                inputPlayerName = ""
                            },
                            readOnly = false,
                            onNameChange = { inputPlayerName = it },
                            onAddPlayer = {
                                if (inputPlayerName.isNotBlank()) {
                                    viewModel.addPlayer(inputPlayerName)
                                    inputPlayerName = ""
                                    showPlayerInputField = false
                                }
                            },
                        )
                    } else {
                        AddPlayerButton(onClick = { showPlayerInputField = true })
                    }
                }
            }

            // Start Game Button
            Button(
                onClick = { navController.navigate("game") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Start Game",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.press_start_2p)),
                    color = Color.White
                )
            }
        }

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background_player_select2),
            contentDescription = stringResource(id = R.string.background_player_select_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .zIndex(-1f)
        )
    }
}
