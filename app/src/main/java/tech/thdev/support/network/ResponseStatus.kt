package tech.thdev.support.network

sealed class ResponseStatus<T : Any> {
    class Success<T : Any>(val item: T) : ResponseStatus<T>()
    class Fail(val exception: Throwable) : ResponseStatus<Throwable>()
}