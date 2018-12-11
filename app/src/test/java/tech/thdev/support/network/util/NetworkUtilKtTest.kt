package tech.thdev.support.network.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import tech.thdev.contract.TEST_FAIL_URL
import tech.thdev.contract.TEST_URL
import tech.thdev.support.network.HTTP_GET
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * HttpsRequestUtil의 use에 대한 test
 * ok/fail 케이스를 테스트한다.
 */
class NetworkUtilKtTest {

    /**
     * http_ok 테스트
     */
    @Test
    fun testHttpOk() = runBlocking {
        val connect = CoroutineScope(Dispatchers.IO).async {
            URL(TEST_URL).use {
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
    fun testHttpNotFound() = runBlocking {
        val connect = CoroutineScope(Dispatchers.IO).async {
            URL(TEST_FAIL_URL).use {
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