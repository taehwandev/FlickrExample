package tech.thdev.instagramexample.network

import kotlinx.coroutines.*
import org.junit.Test
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * HttpsRequest use test
 */
class NetworkUtilKtTest {

    private val testUrl = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=34361b81e70e441b73c62c076aaeac27&format=json&nojsoncallback=1&api_sig=f7a06fcc1d7217501efdb1e5fbdebfb8"

    @Test
    fun test() = runBlocking{
        val connect = CoroutineScope(Dispatchers.IO).async {
            URL(testUrl).use {
                println(testUrl)
                // Default test
                readTimeout = 3000
                connectTimeout = 3000
                requestMethod = HTTP_GET
                connect()

                // request test
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw Exception("URLConnection error $responseCode")
                }

                println("inputStream read ${inputStream?.read()}")

                return@use responseCode
            }
        }

        val ret = connect.await()
        println(ret)
        assert(ret == HttpsURLConnection.HTTP_OK)
    }
}