package tech.thdev.support.network.api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import tech.thdev.coroutines.provider.DispatchersProvider
import tech.thdev.support.data.Response
import tech.thdev.support.network.HTTP_GET
import tech.thdev.support.network.UTF_8
import tech.thdev.support.network.util.readStream
import tech.thdev.support.network.util.use
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NetworkAPI(
        private val useReadTimeout: Int,
        private val useConnectTimeout: Int,
        private val useRequestMethod: String = HTTP_GET,
        private val dec: String = UTF_8,
        private val defaultDispatcher: DispatchersProvider = DispatchersProvider) {

    private lateinit var response: Response

    private lateinit var job: Job

    fun load(url: String): NetworkAPI {
        job = CoroutineScope(defaultDispatcher.default).async {
            url.createConnection().use {
                println(this.url)
                // default setting
                readTimeout = useReadTimeout
                connectTimeout = useConnectTimeout
                requestMethod = useRequestMethod

                // 연결 시도
                connect()

                // request test
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    response = Response(responseMessage, responseCode)
                    return@use
                }

                response = Response(inputStream?.readStream(dec), responseCode)
            }
        }
        return this
    }

    private fun String.createConnection() =
            URL(this)

    suspend fun join(): NetworkAPI {
        job.join()
        return this
    }

    fun cancel() {
        job.cancel()
    }

    suspend fun cancelAndJoin() {
        job.cancelAndJoin()
    }

    fun get() =
            if (::response.isInitialized) {
                response
            } else {
                Response()
            }
}