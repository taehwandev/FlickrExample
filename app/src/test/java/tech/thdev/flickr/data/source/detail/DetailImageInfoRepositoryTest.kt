package tech.thdev.flickr.data.source.detail

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tech.thdev.flickr.network.FlickrApi

class DetailImageInfoRepositoryTest {

    private lateinit var detailImageInfoRepository: DetailImageInfoRepository

    @Before
    fun setUp() {
        detailImageInfoRepository = DetailImageInfoRepository.getInstance(FlickrApi)
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