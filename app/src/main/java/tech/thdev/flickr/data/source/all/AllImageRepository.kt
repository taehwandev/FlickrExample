package tech.thdev.flickr.data.source.all

import io.reactivex.Single
import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.network.FlickrApi

class AllImageRepository private constructor(private val flickrApi: FlickrApi,
                                             private val apiKey: String) {

    companion object {

        // For Singleton instantiation
        private var instance: AllImageRepository? = null

        fun getInstance(flickrApi: FlickrApi, apiKey: String = BuildConfig.FLICKR_API_KEY) =
                instance ?: synchronized(this) {
                    instance
                            ?: AllImageRepository(flickrApi, apiKey).also { instance = it }
                }
    }

    fun loadImage(): Single<DefaultPhotoResponse> =
            flickrApi.loadFlickrDefault(page = 1, perPage = PER_PAGE, apiKey = apiKey)
}