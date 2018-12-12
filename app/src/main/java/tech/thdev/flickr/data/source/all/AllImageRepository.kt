package tech.thdev.flickr.data.source.all

import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.support.data.Response
import tech.thdev.support.network.ResponseStatus
import tech.thdev.support.network.api.enqueue

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

        flickrApi.loadFlickrDefault(page = nowPage, perPage = PER_PAGE, apiKey = apiKey).enqueue().run {
            when (this) {
                is ResponseStatus.Success<*> -> {
                    (this.item as DefaultPhotoResponse).let { item ->
                        // 중간에서 페이지 정보를 확인하고, convert 한다.
                        if (item.status == "ok") {
                            item.photos.run {
                                ++this@AllImageRepository.nowPage
                                this@AllImageRepository.pages = pages
                            }
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