package tech.thdev.flickr.network

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.network.util.createRetrofit
import tech.thdev.support.network.ResponseStatus
import tech.thdev.support.network.api.enqueue

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
        flickrApi.loadFlickrDefault(page = 1, perPage = PER_PAGE).enqueue().run {
            when (this) {
                is ResponseStatus.Success<*> -> {
                    (this.item as DefaultPhotoResponse).let { item ->
                        if (item.status == "ok") {
                            assert(item.photos.photo.isNotEmpty())
                        } else {
                            println("status is not ok")
                            assert(item.status == "fail")
                        }
                    }
                }
                is ResponseStatus.Fail -> {
                    println(this.exception.message)
                }
            }
        }
    }
}