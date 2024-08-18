package com.hack.drinkingacademy.common.local

// androidMain
import android.util.Log

actual object KmmLogger {
    actual fun log(message: String, tag: String, level: LogLevel) {
        when (level) {
            LogLevel.VERBOSE -> Log.v(tag, message)
            LogLevel.DEBUG -> Log.d(tag, message)
            LogLevel.INFO -> Log.i(tag, message)
            LogLevel.WARN -> Log.w(tag, message)
            LogLevel.ERROR -> Log.e(tag, message)
        }
    }
}
