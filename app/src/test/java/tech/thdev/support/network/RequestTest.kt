package tech.thdev.support.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import tech.thdev.contract.TEST_FAIL_URL
import tech.thdev.contract.TEST_URL
import tech.thdev.flickr.data.FlickrPhotoResponse
import tech.thdev.support.network.addon.parse
import tech.thdev.support.network.api.convertParse
import tech.thdev.support.network.api.enqueue
import tech.thdev.support.network.api.request
import tech.thdev.support.network.util.readStream
import tech.thdev.support.network.util.use
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RequestTest {


    /**
     * connect 직접 구현하고, read에 대한 검증
     */
    @Test
    fun testReadStream() = runBlocking {
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

                inputStream?.readStream()
            }
        }

        val ret = connect.await()
        println(ret)
        assert(ret != null)
    }

    /**
     * request api를 활용해서 test
     */
    @Test
    fun testRequestAndReadStream() = runBlocking {
        val ret = TEST_URL.request().join()
        println(ret.get())
        assert(ret.get().requestCode == HttpsURLConnection.HTTP_OK)
    }

    /**
     * Json 데이터를 Data 객체로 변경
     */
    @Test
    fun testJsonToData() = runBlocking {
        val ret = TEST_URL.request().join()
        println(ret.get().message)

        ret.enqueue { result, response ->
            when (result) {
                ResponseStatus.Success -> {
                    assert(response.requestCode == HttpsURLConnection.HTTP_OK)
                    println("success")

                    val item = response.message?.let {
                        FlickrPhotoResponse::class.java.parse(it)
                    }
                    assert(item?.photos?.photo?.isNotEmpty() == true)
                }
                ResponseStatus.Fail -> {
                    assert(response.requestCode != HttpsURLConnection.HTTP_OK)
                    println("fail")
                }
            }
        }
    }

    @Test
    fun testUrlNotFound() = runBlocking {
        val ret = TEST_FAIL_URL.request().join()
        println(ret.get().message)
    }

    @Test
    fun testConvertParse() = runBlocking {
        val ret = "$TEST_URL&page=1".request().join()
        ret.enqueue { result, response ->
            when (result) {
                ResponseStatus.Success -> {
                    assert(response.requestCode != HttpsURLConnection.HTTP_OK)
                    println("fail")
                }
                ResponseStatus.Fail -> {
                    assert(response.requestCode == HttpsURLConnection.HTTP_OK)
                    println("success")

                    println("jsonQuery ${ret.get().message}")

                    val item = response.convertParse(FlickrPhotoResponse::class.java)
                    println("item $item")
                    assert(item?.photos?.photo?.isNotEmpty() == true)
                }
            }
        }
    }
}