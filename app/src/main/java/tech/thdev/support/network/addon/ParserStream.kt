@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.support.network.addon

import com.google.gson.Gson

/**
 * String to gson
 */
inline fun <T> Class<T>.parse(jsonData: String, gson: Gson = Parser.gson): T =
        gson.fromJson<T>(jsonData, this)