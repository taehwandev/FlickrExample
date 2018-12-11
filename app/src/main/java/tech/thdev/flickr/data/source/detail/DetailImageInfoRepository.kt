package tech.thdev.flickr.data.source.detail

import tech.thdev.flickr.data.PhotoDetail
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.support.data.Response
import tech.thdev.support.network.ResponseStatus
import tech.thdev.support.network.api.NetworkAPI
import tech.thdev.support.network.api.convertParse
import tech.thdev.support.network.api.enqueue
import tech.thdev.support.network.api.request

class DetailImageInfoRepository private constructor(private val flickrApi: FlickrApi) {

    companion object {

        // For Singleton instantiation
        private var instance: DetailImageInfoRepository? = null

        fun getInstance(flickrApi: FlickrApi) =
                instance ?: synchronized(this) {
                    instance
                            ?: DetailImageInfoRepository(flickrApi).also { instance = it }
                }
    }

    suspend fun loadDetail(photoId: String,
                           onError: suspend (response: Response) -> Unit,
                           onSuccess: suspend (response: PhotoDetail) -> Unit): NetworkAPI =
            flickrApi.loadPhotoDetail(photoId).request().join().also { network ->
                network.enqueue { result, response ->
                    when (result) {
                        ResponseStatus.Success -> {
                            // 중간에서 페이지 정보를 확인하고, convert 한다.
                            println(response.message)
                            response.convertParse(PhotoDetail::class.java)?.let {
                                onSuccess(it)
                            } ?: onError(response)
                        }
                        ResponseStatus.Fail -> {
                            onError(response)
                        }
                    }
                }
            }
}