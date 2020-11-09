package tech.thdev.flickr.data.source.detail

import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.data.PhotoDetail
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.support.data.Response

class DetailImageInfoRepository private constructor(
    private val flickrApi: FlickrApi,
    private val apiKey: String
) {

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
        onSuccess: suspend (response: PhotoDetail) -> Unit
    ) {
        flickrApi.loadPhotoDetail(photoId = photoId, apiKey = apiKey).run {
            try {
                if (stat == "ok") {
                    onSuccess(this)
                } else {
                    onError(Response(message))
                }
            } catch (e: Exception) {
                onError(Response(e.message))
            }
        }
    }
}