package com.hack.drinkingacademy.android.player_select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hack.drinkingacademy.android.R

@Composable
fun PlayerCard(
    name: String,
    onCloseClick: () -> Unit,
    readOnly: Boolean = true,
    onNameChange: (String) -> Unit = {},
    onAddPlayer: () -> Unit = {},
) {
    // A focus requester to request focus for the text field when adding a new player
    val focusRequester = remember { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xAA000000)) // Match background with the game craziness level box
            .padding(8.dp)

    ) {
        if (readOnly) {
            Text(
                text = name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.rubik)),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        } else {
            TextField(
                value = name,
                onValueChange = onNameChange,
//                label = { Text("Name", color = Color.White) },
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.rubik)),
                    color = Color.White,
                    fontSize = 18.sp
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (name.isNotBlank()) {
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
                    .focusRequester(focusRequester)
            )
        }
        IconButton(onClick = { onCloseClick() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove player",
                tint = Color.Red // Red to make the close icon stand out
            )
        }
    }

    LaunchedEffect(readOnly) {
        if (!readOnly) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
@Preview
fun PlayerCardPreview() {
    PlayerCard("Robert", {})
}