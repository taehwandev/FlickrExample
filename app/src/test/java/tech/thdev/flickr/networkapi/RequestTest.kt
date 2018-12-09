package tech.thdev.flickr.networkapi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Test
import tech.thdev.flickr.data.FlickrPhotoResponse
import tech.thdev.support.network.HTTP_GET
import tech.thdev.support.network.addon.parse
import tech.thdev.support.network.api.convertParse
import tech.thdev.support.network.api.enqueue
import tech.thdev.support.network.api.request
import tech.thdev.support.network.util.readStream
import tech.thdev.support.network.util.use
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RequestTest {

    private val testUrl = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=34361b81e70e441b73c62c076aaeac27&format=json&nojsoncallback=1"

    /**
     * connect 직접 구현하고, read에 대한 검증
     */
    @Test
    fun testReadStream() = runBlocking {
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
        val ret = testUrl.request().join()
        println(ret.get())
        assert(ret.get().requestCode == HttpsURLConnection.HTTP_OK)
    }

    /**
     * Json 데이터를 Data 객체로 변경
     */
    @Test
    fun testJsonToData() = runBlocking {
        val ret = testUrl.request().join()
        println(ret.get().message)


        ret.enqueue(onError = {
            assert(it.requestCode != HttpsURLConnection.HTTP_OK)
            println("fail")
        }) {
            assert(ret.get().requestCode == HttpsURLConnection.HTTP_OK)
            println("success")

            val item = ret.get().message?.let {
                FlickrPhotoResponse::class.java.parse(it)
            }
            assert(item?.photos?.photo?.isNotEmpty() == true)
        }
    }

    @Test
    fun testUrlNotFound() = runBlocking {
        val ret = "https://api.flickr.com/services/a".request().join()
        println(ret.get().message)
    }

    @Test
    fun testConvertParse() = runBlocking {
        val ret = "$testUrl&page=1".request().join()
        ret.enqueue(onError = {
            assert(it.requestCode != HttpsURLConnection.HTTP_OK)
            println("fail")
        }) {
            assert(ret.get().requestCode == HttpsURLConnection.HTTP_OK)
            println("success")

            println("jsonQuery ${ret.get().message}")

            val item = it.convertParse(FlickrPhotoResponse::class.java)
            println("item $item")
            assert(item?.photos?.photo?.isNotEmpty() == true)
        }
    }
}