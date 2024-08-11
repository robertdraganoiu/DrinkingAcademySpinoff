package com.hack.drinkingacademy.android.player_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.hack.drinkingacademy.android.R
import java.util.*

@Composable
fun PlayerSelectScreen(
    viewModel: PlayerSelectViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val players by viewModel.players.collectAsState()
    var showTextField by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf("") }
    var crazinessLevel by remember { mutableStateOf(1f) }

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
                crazinessLevel = newValue
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
                columns = GridCells.Adaptive(minSize = 120.dp),
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
                    if (showTextField) {
                        PlayerInputCard(
                            playerName = playerName,
                            onPlayerNameChange = { playerName = it },
                            onAddPlayer = {
                                if (playerName.isNotBlank()) {
                                    viewModel.addPlayer(playerName)
                                    playerName = ""
                                }
                            },
                            onCancel = {
                                showTextField = false
                                playerName = ""
                            }
                        )
                    } else {
                        AddPlayerButton(onClick = { showTextField = true })
                    }
                }
            }

            // Start Game Button
            Button(
                onClick = { navController.navigate("game_mode_select") },
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
            painter = painterResource(id = R.drawable.background_player_select),
            contentDescription = stringResource(id = R.string.background_player_select_description),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .zIndex(-1f)
        )
    }
}

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

@Composable
fun AddPlayerButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .background(Color.Green, shape = CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Player",
            tint = Color.White,
            modifier = Modifier.size(36.dp)
        )
    }
}

@Composable
fun PlayerInputCard(
    playerName: String,
    onPlayerNameChange: (String) -> Unit,
    onAddPlayer: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color(0xAA000000), RoundedCornerShape(10.dp))
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) {
                onCancel()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = playerName,
                onValueChange = onPlayerNameChange,
                label = { Text("Enter Player Name", color = Color.White) },
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.rubik)),
                    color = Color.White,
                    fontSize = 16.sp
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (playerName.isNotBlank()) {
                        onAddPlayer()
                    }
                }),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Cyan,
                    unfocusedIndicatorColor = Color.Gray,
                    textColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            IconButton(onClick = { onCancel() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cancel",
                    tint = Color.Red // Red to make the close icon stand out
                )
            }
        }
    }
}

fun Int.toCrazinessLevelString(): String = when (this) {
    1 -> "1 - Sunny"
    2 -> "2 - Breezy"
    3 -> "3 - Tropical"
    4 -> "4 - Wild"
    else -> "5 - Hurricane"
}
