package tech.thdev.flickr.network

import tech.thdev.flickr.BuildConfig

object FlickrApi {

    private const val FLICKR_DEFAULT_ADDRESS = "https://api.flickr.com/services/rest/"

    //https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4a88c79b8b25ddfcf8f06d78ee5d25e8&per_page=20&page=25&format=json&nojsoncallback=1
    private const val FLICKR_LOAD_RECENT_IMAGE = "?method=flickr.interestingness.getList&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1"

    // https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=06486cf47869200a22d27c4d21d48c09&photo_id=44437878270&format=json&nojsoncallback=1&api_sig=7969880c1464e9854d1b23cf206289f7
    private const val FLICKR_PHOTO_DETAIL = "?method=flickr.photos.getInfo&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1"

    private fun String.loadDefaultUrl(): String =
            "$FLICKR_DEFAULT_ADDRESS$this"

    fun loadFlickrDefault(page: Int, perPage: Int): String =
            "${FLICKR_LOAD_RECENT_IMAGE.loadDefaultUrl()}&page=$page&per_page=$perPage"

    fun loadPhotoDetail(photoId: String): String =
            "${FLICKR_PHOTO_DETAIL.loadDefaultUrl()}&photo_id=$photoId"
}

