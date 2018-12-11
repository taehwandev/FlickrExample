package tech.thdev.flickr.data.source.all

import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.data.FlickrPhotoResponse
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.util.d
import tech.thdev.support.data.Response
import tech.thdev.support.network.ResponseStatus
import tech.thdev.support.network.api.NetworkAPI
import tech.thdev.support.network.api.convertParse
import tech.thdev.support.network.api.enqueue
import tech.thdev.support.network.api.request

class AllImageRepository private constructor(private val flickrApi: FlickrApi) {

    companion object {

        // For Singleton instantiation
        private var instance: AllImageRepository? = null

        fun getInstance(flickrApi: FlickrApi) =
                instance ?: synchronized(this) {
                    instance
                            ?: AllImageRepository(flickrApi).also { instance = it }
                }
    }

    private val defaultPage = 1

    private var nowPage = defaultPage
    private var pages = 0

    val isMoreLoad: Boolean
        get() = (pages > 0 && pages >= nowPage) || (nowPage == defaultPage && pages == 0)

    fun clear() {
        nowPage = defaultPage
    }

    suspend fun loadImage(
            onError: suspend (response: Response) -> Unit,
            onSuccess: suspend (response: FlickrPhotoResponse) -> Unit): NetworkAPI =
            flickrApi.loadFlickrDefault(nowPage, PER_PAGE).request().join().also { network ->
                network.enqueue { result, response ->
                    when (result) {
                        ResponseStatus.Success -> {
                            // 중간에서 페이지 정보를 확인하고, convert 한다.
                            d { response.message ?: "" }
                            response.convertParse(FlickrPhotoResponse::class.java)?.let {
                                it.photos.run {
                                    this@AllImageRepository.nowPage = page
                                    this@AllImageRepository.pages = pages
                                }

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