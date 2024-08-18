package com.hack.drinkingacademy.common.local

expect object KmmLogger {
    fun log(message: String, tag: String = "BUZZ", level: LogLevel = LogLevel.DEBUG)
}

enum class LogLevel {
    VERBOSE, DEBUG, INFO, WARN, ERROR
}
