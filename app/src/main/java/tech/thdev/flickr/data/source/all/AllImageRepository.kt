package tech.thdev.flickr.data.source.all

import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.contract.PER_PAGE
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.support.data.Response

/**
 * 2편 샘플 코드 - Property와 function 정의에서 알아두면 좋은 것
 */
class AllImageRepository private constructor(
    private val flickrApi: FlickrApi,
    private val apiKey: String
) {

    companion object {

        // For Singleton instantiation
        private var instance: AllImageRepository? = null

        fun getInstance(flickrApi: FlickrApi, apiKey: String = BuildConfig.FLICKR_API_KEY) =
            instance ?: synchronized(this) {
                instance ?: AllImageRepository(flickrApi, apiKey).also { instance = it }
            }
    }

    private val defaultPage = 1

    private var nowPage = defaultPage
    private var pages = 0

    /**
     * 잘못 사용한 예.
     */
    val DefaultPhotoResponse.successAndNextPage: Boolean
        get() {
            return (status == "ok").also {
                if (it) {
                    this@AllImageRepository.nowPage++
                } else {
                    this@AllImageRepository.nowPage
                }
                this@AllImageRepository.pages = photos.pages
            }
        }

    /**
     * 이렇게 함수로 표현하는게 더 맞다.
     */
    fun DefaultPhotoResponse.checkSuccessAndNextPage(): Boolean {
        return (status == "ok").also {
            if (it) {
                this@AllImageRepository.nowPage++
            } else {
                this@AllImageRepository.nowPage
            }
            this@AllImageRepository.pages = photos.pages
        }
    }

    // Property 예 = true/false 만 리턴한다.
    val isMoreLoad: Boolean
        get() = (pages > 0 && pages >= nowPage)
                || (nowPage == defaultPage && pages == 0)

    fun clear() {
        nowPage = defaultPage
    }

    suspend fun loadImage(
        onError: suspend (response: Response) -> Unit,
        onSuccess: suspend (response: DefaultPhotoResponse) -> Unit
    ) {
        if (!isMoreLoad) {
            // 더 이상 부를게 없을 경우
            return
        }

        flickrApi.loadFlickrDefault(page = nowPage, perPage = PER_PAGE, apiKey = apiKey).run {
            // 중간에서 페이지 정보를 확인하고, convert 한다.
            try {
                // 다음의 코드를 Property를 이용해 수정해보자.
                if (status == "ok") {
                    photos.run {
                        ++this@AllImageRepository.nowPage
                        this@AllImageRepository.pages = pages
                    }
                    onSuccess(this)
                } else {
                    onError(Response(this.message))
                }

            } catch (e: Exception) {
                onError(Response(e.message))
            }
        }
    }
}