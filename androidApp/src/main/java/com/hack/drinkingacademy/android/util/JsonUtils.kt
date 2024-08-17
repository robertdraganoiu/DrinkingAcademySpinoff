package com.hack.drinkingacademy.android.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {
    val gson = Gson()
}


// Extension function to convert an object to a JSON string
fun <T> T.toJson(): String = JsonUtils.gson.toJson(this)

// Extension function to convert a JSON string to an object of type T
inline fun <reified T> String.fromJson(): T = JsonUtils.gson.fromJson(this, object : TypeToken<T>() {}.type)