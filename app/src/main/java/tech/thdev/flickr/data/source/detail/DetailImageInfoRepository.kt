package tech.thdev.flickr.data.source.detail

import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.data.PhotoDetail
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.support.data.Response
import tech.thdev.support.network.ResponseStatus
import tech.thdev.support.network.api.enqueue

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

    suspend fun loadDetail(
            photoId: String,
            onError: suspend (response: Response) -> Unit,
            onSuccess: suspend (response: PhotoDetail) -> Unit) {
        flickrApi.loadPhotoDetail(photoId = photoId, apiKey = apiKey).enqueue().run {
            when (this) {
                is ResponseStatus.Success<*> -> {
                    (this.item as PhotoDetail).let { item ->
                        if (item.stat == "ok") {
                            onSuccess(item)
                        } else {
                            onError(Response(item.message))
                        }
                    }
                }
                is ResponseStatus.Fail -> {
                    onError(Response(this.exception.message))
                }
            }
        }
    }
}