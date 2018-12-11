package tech.thdev.flickr.data.source.all

import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.util.d
import tech.thdev.support.data.Response
import tech.thdev.support.network.ResponseStatus
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

    suspend fun loadImage(onError: suspend (response: Response) -> Unit,
                          onSuccess: suspend (response: DefaultPhotoResponse) -> Unit) {
        if (!isMoreLoad) {
            // 더 이상 부를게 없을 경우
            return
        }
        flickrApi.loadFlickrDefault(nowPage, PER_PAGE).request().join().also { network ->
            network.enqueue { result, response ->
                when (result) {
                    ResponseStatus.Success -> {
                        // 중간에서 페이지 정보를 확인하고, convert 한다.
                        d { response.message ?: "" }
                        response.convertParse(DefaultPhotoResponse::class.java)?.let {
                            if (it.status == "ok") {
                                it.photos.run {
                                    ++this@AllImageRepository.nowPage
                                    this@AllImageRepository.pages = pages
                                }

                                onSuccess(it)
                            } else {
                                onError(Response(it.message, requestCode = it.code))
                            }
                        } ?: onError(response)
                    }
                    ResponseStatus.Fail -> {
                        pages = 1
                        nowPage = defaultPage
                        onError(response)
                    }
                }
            }
        }
    }
}