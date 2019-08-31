package tech.thdev.flickr.data.source.detail

import io.reactivex.Single
import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.data.PhotoDetail
import tech.thdev.flickr.network.FlickrApi

class DetailImageInfoRepository private constructor(private val flickrApi: FlickrApi,
                                                    private val apiKey: String) {

    companion object {

        // For Singleton instantiation
        private var instance: DetailImageInfoRepository? = null

        fun getInstance(flickrApi: FlickrApi, apiKey: String = BuildConfig.FLICKR_API_KEY) =
                instance ?: synchronized(this) {
                    instance
                            ?: DetailImageInfoRepository(flickrApi, apiKey).also { instance = it }
                }
    }

    fun loadDetail(photoId: String): Single<PhotoDetail> =
            flickrApi.loadPhotoDetail(photoId = photoId, apiKey = apiKey)
}