package tech.thdev.flickr.network

import tech.thdev.flickr.BuildConfig

object FlickrApi {

    private const val FLICKR_DEFAULT_ADDRESS = "https://api.flickr.com/services/rest/"

    //https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4a88c79b8b25ddfcf8f06d78ee5d25e8&per_page=20&page=25&format=json&nojsoncallback=1
    private const val FLICKR_LOAD_RECENT_IMAGE = "?method=flickr.interestingness.getList&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1"

    const val FLICKR_DETAIL_CALL = ""

    private fun String.loadDefaultUrl(): String =
            "$FLICKR_DEFAULT_ADDRESS$this"

    fun loadFlickrDefault(page: Int, perPage: Int): String =
            "${FLICKR_LOAD_RECENT_IMAGE.loadDefaultUrl()}&page=$page&per_page=$perPage"
}

