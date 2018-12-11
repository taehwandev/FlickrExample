package tech.thdev.support.network.api

import kotlinx.coroutines.runBlocking
import org.junit.Test
import tech.thdev.contract.TEST_URL
import javax.net.ssl.HttpsURLConnection

class NetworkAPITest {

    /**
     * 성공케이스 테스트
     */
    @Test
    fun onNetworkApiTest() = runBlocking {
        val ret = NetworkAPI(3000, 3000).apply {
            load(TEST_URL)
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
            load(TEST_URL)
        }
        ret.cancel()
        val response = ret.get()
        println("requestCode ${response.requestCode}")
        println("requestMessage ${response.message}")
        assert(response.requestCode == HttpsURLConnection.HTTP_CREATED)
    }
}