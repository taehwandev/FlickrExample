package tech.thdev.flickr.networkapi

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tech.thdev.support.network.api.NetworkAPI
import javax.net.ssl.HttpsURLConnection

class NetworkAPITest {

    private val testUrl =
        "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=34361b81e70e441b73c62c076aaeac27&format=json&nojsoncallback=1&api_sig=f7a06fcc1d7217501efdb1e5fbdebfb8"

    @Before
    fun setUp() {
    }

    /**
     * 성공케이스 테스트
     */
    @Test
    fun onNetworkApiTest() = runBlocking {
        val ret = NetworkAPI(3000, 3000).apply {
            load(testUrl)
        }
        ret.join()
        println("requestCode ${ret.get().requestCode}")
        println("requestMessage ${ret.get().message}")
        assert(ret.get().requestCode == HttpsURLConnection.HTTP_OK)
    }

    /**
     * 즉시 cancel
     */
    @Test
    fun onNetworkApiCancel() = runBlocking {
        val ret = NetworkAPI(3000, 3000).apply {
            load(testUrl)
        }
        ret.cancel()
        val response = ret.get()
        println("requestCode ${response.requestCode}")
        println("requestMessage ${response.message}")
        assert(response.requestCode == HttpsURLConnection.HTTP_CREATED)
    }
}