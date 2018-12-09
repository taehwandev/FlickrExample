package tech.thdev.flickr.networkapi

import kotlinx.coroutines.*
import org.junit.Test
import tech.thdev.support.network.HTTP_GET
import tech.thdev.support.network.util.use
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * HttpsRequestUtil의 use에 대한 test
 * ok/fail 케이스를 테스트한다.
 */
class NetworkUtilKtTest {

    private val testUrl = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=34361b81e70e441b73c62c076aaeac27&format=json&nojsoncallback=1&api_sig=f7a06fcc1d7217501efdb1e5fbdebfb8"

    /**
     * http_ok 테스트
     */
    @Test
    fun testHttpOk() = runBlocking{
        val connect = CoroutineScope(Dispatchers.IO).async {
            URL(testUrl).use {
                println(this.url.toString())
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

    /**
     * http 404 test
     */
    @Test
    fun testHttpNotFound() = runBlocking{
        val connect = CoroutineScope(Dispatchers.IO).async {
            URL("https://api.flickr.com/services/a").use {
                println(this.url.toString())
                // Default test
                readTimeout = 3000
                connectTimeout = 3000
                requestMethod = HTTP_GET
                connect()

                // request test
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    return@use responseCode
                }

                println("inputStream read ${inputStream?.read()}")

                return@use responseCode
            }
        }

        val ret = connect.await()
        println(ret)
        assert(ret == HttpsURLConnection.HTTP_NOT_FOUND)
    }
}