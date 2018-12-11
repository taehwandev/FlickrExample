package tech.thdev.support.network

sealed class ResponseStatus {
    object Success : ResponseStatus()
    object Fail : ResponseStatus()
}
