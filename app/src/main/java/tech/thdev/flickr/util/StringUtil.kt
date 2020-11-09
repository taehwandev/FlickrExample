@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <T> String?.notNullMessage(block: (message: String) -> T?): T? {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (!this.isNullOrEmpty()) {
        block(this)
    } else null
}

inline fun String.addComma(): String =
        try {
            this.toInt().addComma()
        } catch (e: Exception) {
            this
        }

inline fun Int.addComma(): String =
        String.format("%,2d", this)