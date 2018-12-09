package tech.thdev.support.network.api

import tech.thdev.coroutines.provider.DispatchersProvider
import tech.thdev.support.data.Response
import tech.thdev.support.network.DEFAULT_CONNECT_TIMEOUT
import tech.thdev.support.network.DEFAULT_READ_TIMEOUT
import tech.thdev.support.network.HTTP_GET
import tech.thdev.support.network.UTF_8
import javax.net.ssl.HttpsURLConnection

fun String.request(
        readTimeout: Int = DEFAULT_READ_TIMEOUT,
        connectTimeout: Int = DEFAULT_CONNECT_TIMEOUT,
        requestMethod: String = HTTP_GET,
        dec: String = UTF_8,
        dispatcher: DispatchersProvider = DispatchersProvider
): NetworkAPI =
        NetworkAPI(readTimeout, connectTimeout, requestMethod, dec, dispatcher)
                .load(this)

suspend fun NetworkAPI.enqueue(onError: suspend (response: Response) -> Unit,
                               onSuccess: suspend (response: Response) -> Unit) {
    if (get().requestCode != HttpsURLConnection.HTTP_OK) {
        onError(get())
    } else {
        onSuccess(get())
    }
}