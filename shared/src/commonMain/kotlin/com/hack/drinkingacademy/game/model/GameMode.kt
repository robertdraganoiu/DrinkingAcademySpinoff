package com.hack.drinkingacademy.game.model

data class GameMode(
    val id: Long,
    val name: String,
    val description: String,
    val isEnabled: Boolean
)