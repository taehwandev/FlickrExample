@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.support.network.api

import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import tech.thdev.support.network.ResponseStatus

suspend fun <T : Any> Deferred<T>.enqueue(): ResponseStatus<*> =
    try {
        ResponseStatus.Success(this.await())
    } catch (e: HttpException) {
        ResponseStatus.Fail(e)
    } catch (e: Throwable) {
        ResponseStatus.Fail(e)
    }