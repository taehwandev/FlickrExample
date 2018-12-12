package tech.thdev.flickr.data.source.all

import kotlinx.coroutines.runBlocking
import org.junit.Test
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.network.util.createRetrofit

class AllImageRepositoryTest {

    private lateinit var allImageRepository: AllImageRepository

    @Test
    fun testLoad() = runBlocking {
        allImageRepository = AllImageRepository.getInstance(createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            true
        })

        allImageRepository.loadImage(onError = {
            println("error $it")
        }) {
            assert(it.status == "ok")
        }
        Unit
    }

    @Test
    fun testMoreLoad() = runBlocking {
        allImageRepository = AllImageRepository.getInstance(createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            true
        })

        allImageRepository.loadImage(onError = {
            println("error $it")
        }) {
            assert(it.status == "ok")
            assert(it.photos.page == 1)
        }
        allImageRepository.loadImage(onError = {
            println("error $it")
        }) {
            assert(it.status == "ok")
            assert(it.photos.page == 2)
        }
        allImageRepository.loadImage(onError = {
            println("error $it")
        }) {
            assert(it.status == "ok")
            assert(it.photos.page == 3)
        }
    }

    @Test
    fun testApiKeyError() = runBlocking {
        allImageRepository = AllImageRepository.getInstance(createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            true
        }, apiKey = "key errorror")
        allImageRepository.loadImage(onError = {
            assert("Invalid API Key (Key has invalid format)" == it.message)
        }) {
            // fail
        }
    }
}