package com.hack.drinkingacademy.android.player_select

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
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
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.hack.drinkingacademy.android.util.toJson
import com.hack.drinkingacademy.common.constants.Constants
import kotlinx.coroutines.launch

@Composable
fun PlayerSelectScreen(
    viewModel: PlayerSelectViewModel = hiltViewModel(), navController: NavHostController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val players by viewModel.players.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()
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
            DifficultyLevelSlider(difficulty) { newValue ->
                viewModel.setDifficulty(newValue)
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
                    PlayerCard(name = players[index],
                        onCloseClick = { viewModel.removePlayer(players[index]) })
                }

                // Add Player Button and TextField
                if (players.size < Constants.MAX_PLAYERS) {
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
            }

            // Start Game Button
            Button(
                onClick = {
                    if (players.isNotEmpty()) {
                        navController.navigate("game/${players.toJson()}/$difficulty")
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Add players before starting the game!")
                        }
                    }
                }, modifier = Modifier
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

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // Animatable values for position (x, y) and scale
        val offsetX = remember { Animatable(0f) }
        val offsetY = remember { Animatable(0f) }
        val scale = remember { Animatable(1.05f) }

        // Image size in relation to the container
        val maxOffsetX = 30f // Limit horizontal movement to 30 pixels
        val maxOffsetY = 30f // Limit vertical movement to 30 pixels
        val maxScale = 1.15f // Slightly larger scale for zoom-in effect

        // Launch the infinite animation loop
        LaunchedEffect(Unit) {
            launch {
                // Animate horizontally with constrained motion
                offsetX.animateTo(
                    targetValue = maxOffsetX, // Move right by maxOffsetX pixels
                    animationSpec = infiniteRepeatable(
                        animation = tween(8000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }

            launch {
                // Animate vertically with constrained motion
                offsetY.animateTo(
                    targetValue = maxOffsetY, // Move down by maxOffsetY pixels
                    animationSpec = infiniteRepeatable(
                        animation = tween(10000, easing = LinearOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )
            }

            launch {
                // Animate zoom in and out within the specified range
                scale.animateTo(
                    targetValue = maxScale, // Scale up to maxScale
                    animationSpec = infiniteRepeatable(
                        animation = tween(7000, easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)),
                        repeatMode = RepeatMode.Reverse
                    ),
                    initialVelocity = 0f // Ensure a smooth transition with no initial velocity
                )
            }
        }


        // Apply the transformations to the Image
        AnimatedBackground()
    }
}

@Composable
fun AnimatedBackground() {
    // Animatable values for position (x, y) and scale
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scale = remember { Animatable(1.05f) }

    // Image size in relation to the container
    val maxOffsetX = 30f // Limit horizontal movement to 30 pixels
    val maxOffsetY = 30f // Limit vertical movement to 30 pixels
    val maxScale = 1.15f // Slightly larger scale for zoom-in effect

    // Launch the infinite animation loop
    LaunchedEffect(Unit) {
        launch {
            // Animate horizontally with constrained motion
            offsetX.animateTo(
                targetValue = maxOffsetX, // Move right by maxOffsetX pixels
                animationSpec = infiniteRepeatable(
                    animation = tween(8000, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }

        launch {
            // Animate vertically with constrained motion
            offsetY.animateTo(
                targetValue = maxOffsetY, // Move down by maxOffsetY pixels
                animationSpec = infiniteRepeatable(
                    animation = tween(10000, easing = LinearOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }

        launch {
            // Animate zoom in and out within the specified range
            scale.animateTo(
                targetValue = maxScale, // Scale up to maxScale
                animationSpec = infiniteRepeatable(
                    animation = tween(7000, easing = CubicBezierEasing(0.42f, 0f, 0.58f, 1f)),
                    repeatMode = RepeatMode.Reverse
                ),
                initialVelocity = 0f // Ensure a smooth transition with no initial velocity
            )
        }
    }

    // Apply the transformations to the Image
    Box(
        modifier = Modifier
            .fillMaxSize() // This ensures the Box takes up all available space
            .zIndex(-1f) // Keep the background behind other content
    ) {
        // Apply the transformations to the Image
        Image(
            painter = painterResource(id = R.drawable.background_player_select),
            contentDescription = stringResource(id = R.string.background_player_select_description),
            contentScale = ContentScale.Crop, // Ensure the image covers the entire area
            modifier = Modifier
                .fillMaxSize() // Make the Image fill the entire Box
                .graphicsLayer(
                    translationX = offsetX.value,
                    translationY = offsetY.value,
                    scaleX = scale.value,
                    scaleY = scale.value
                )
        )
    }
}
