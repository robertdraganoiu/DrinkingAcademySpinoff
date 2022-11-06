package com.hack.drinkingacademy.android.user_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hack.drinkingacademy.android.R

@Preview
@Composable
fun UserScreen(
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadUserDetails()
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(state.avatarId),
            contentDescription = stringResource(state.avatarDescriptionId),
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.5.dp, MaterialTheme.colors.secondary, RoundedCornerShape(12.dp))
        )
        Text(
            text = "Hello, ${state.title.text}!",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
        Button(
            onClick = { viewModel.addProgress(50) }
        ) {
            Text(
                text = "Start game",
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp
            )
        }
    }
}
