package tech.thdev.flickr.data.source.all

import tech.thdev.flickr.data.FlickrPhotoResponse
import tech.thdev.flickr.network.loadFlickrDefault
import tech.thdev.flickr.util.d
import tech.thdev.support.data.Response
import tech.thdev.support.network.api.NetworkAPI
import tech.thdev.support.network.api.convertParse
import tech.thdev.support.network.api.enqueue
import tech.thdev.support.network.api.request

object AllImageRepository {

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
            onSuccess: suspend (response: FlickrPhotoResponse) -> Unit
    ): NetworkAPI =
            loadFlickrDefault(nowPage).request().join().also { network ->
                network.enqueue(onError) { success ->
                    // 중간에서 페이지 정보를 확인하고, convert 한다.
                    d { success.message ?: "" }
                    success.convertParse(FlickrPhotoResponse::class.java)?.let {
                        it.photos.run {
                            this@AllImageRepository.nowPage = page
                            this@AllImageRepository.pages = pages
                        }

                        onSuccess(it)
                    } ?: onError(success)
                }
            }
}