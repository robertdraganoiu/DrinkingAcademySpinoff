package com.hack.drinkingacademy.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hack.drinkingacademy.android.game.GameScreen
import com.hack.drinkingacademy.android.game_mode_select.GameModeSelectScreen
import com.hack.drinkingacademy.android.player_select.PlayerSelectScreen
import com.hack.drinkingacademy.android.user_details.UserScreen
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF00C853), // Tropical Green
            primaryVariant = Color(0xFF0091EA), // Ocean Blue
            secondary = Color(0xFFFF6D00), // Sunset Pink
            background = Color(0xFF121212), // Dark Background
            surface = Color(0xFF1E1E1E), // Dark Surface
            error = Color(0xFFD32F2F), // Error
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.White,
            onSurface = Color.White,
            onError = Color.White
        )
    } else {
        lightColors(
            primary = Color(0xFF00C853), // Tropical Green
            primaryVariant = Color(0xFF0091EA), // Ocean Blue
            secondary = Color(0xFFFF6D00), // Sunset Pink
            background = Color(0xFFF5F5F5), // Light Background
            surface = Color(0xFFFFFFFF), // Light Surface
            error = Color(0xFFD32F2F), // Error
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
            onError = Color.White
        )
    }

    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily(Font(R.font.press_start_2p)), // Pixelated Font
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        h1 = TextStyle(
            fontFamily = FontFamily(Font(R.font.rubik)), // Rounded and playful
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        button = TextStyle(
            fontFamily = FontFamily(Font(R.font.press_start_2p)), // Pixelated Font
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    )

    val shapes = Shapes(
        small = CutCornerShape(4.dp), // Small pixel-like cut corners
        medium = CutCornerShape(8.dp), // More pronounced cuts
        large = RoundedCornerShape(0.dp) // Completely square for pixelated feel
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "player_select") {
                        composable(route = "player_select") {
                            PlayerSelectScreen(navController = navController)
                        }
                        composable(route = "game") {
                            GameScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}