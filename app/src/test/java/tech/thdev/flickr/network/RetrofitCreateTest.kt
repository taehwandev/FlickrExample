package tech.thdev.flickr.network

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.network.util.createRetrofit

class RetrofitCreateTest {

    private lateinit var flickrApi: FlickrApi

    @Before
    fun setUp() {
        flickrApi = createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            true
        }
    }

    @Test
    fun loadTest() = runBlocking {
        flickrApi.loadFlickrDefault(page = 1, perPage = PER_PAGE).run {
            if (status == "ok") {
                assert(photos.photo.isNotEmpty())
            } else {
                println("status is not ok")
                assert(status == "fail")
            }
        }
    }
}