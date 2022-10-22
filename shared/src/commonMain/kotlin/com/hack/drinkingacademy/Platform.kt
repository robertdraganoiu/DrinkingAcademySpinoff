package com.hack.drinkingacademy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform