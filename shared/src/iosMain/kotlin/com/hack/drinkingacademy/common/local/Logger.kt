package com.hack.drinkingacademy.common.local

// iosMain
import platform.Foundation.NSLog

actual object KmmLogger {
    actual fun log(message: String, tag: String, level: LogLevel) {
        val formattedMessage = "[$tag] $message"
        NSLog("%@", formattedMessage)
    }
}
