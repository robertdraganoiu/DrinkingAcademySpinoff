package com.hack.drinkingacademy.android.user_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun UserScreen(
    viewModel: UserDetailsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()

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
//            Image(
//                painter = painterResource(state.avatarId),
//                contentDescription = stringResource(state.avatarDescriptionId),
//                modifier = Modifier
//                    .size(160.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .border(1.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(12.dp))
//            )
            Text(
                text = "Hello, ${state.title.text}!",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Button(
                onClick = {
                    navController.navigate("player_select")
                }
            ) {
                Text(
                    text = "Start game",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }
        }

        // background image
//        Image(
//            painter = painterResource(id = R.drawable.background_user_details),
//            contentDescription = stringResource(id = R.string.background_user_details_description),
//            contentScale = ContentScale.FillBounds,
//            modifier = Modifier
//                .matchParentSize()
//                .zIndex(-1f)
//        )
    }
}
