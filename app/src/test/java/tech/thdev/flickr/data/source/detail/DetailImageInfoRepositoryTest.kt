package tech.thdev.flickr.data.source.detail

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.network.util.createRetrofit

class DetailImageInfoRepositoryTest {

    private lateinit var detailImageInfoRepository: DetailImageInfoRepository

    @Before
    fun setUp() {
        detailImageInfoRepository = DetailImageInfoRepository.getInstance(createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            true
        })
    }

    @Test
    fun testLoadDetail() = runBlocking {
        detailImageInfoRepository.loadDetail("44437878270", onError = {
            println("error $it")
        }) {
            assert(it.photo.photoId == "44437878270")
        }
        Unit
    }
}