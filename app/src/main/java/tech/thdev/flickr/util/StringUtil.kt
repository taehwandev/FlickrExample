package tech.thdev.flickr.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@UseExperimental(ExperimentalContracts::class)
inline fun <T> String?.notNullMessage(block: (message: String) -> T?): T? {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return if (!this.isNullOrEmpty()) {
        block(this)
    } else null
}