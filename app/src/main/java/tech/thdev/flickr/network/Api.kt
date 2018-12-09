package tech.thdev.flickr.network

import tech.thdev.flickr.BuildConfig

const val PER_PAGE = 50
const val FLICKR_DEFAULT_ADDRESS = "https://api.flickr.com/services/rest/"
const val FLICKR_DEFAULT_API = "?method=flickr.interestingness.getList&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1"
//const val FLICKR_DETAIL_CALL = ""
//
//https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4a88c79b8b25ddfcf8f06d78ee5d25e8&per_page=20&page=25&format=json&nojsoncallback=1

fun loadFlickrDefault(page: Int) =
        "$FLICKR_DEFAULT_ADDRESS$FLICKR_DEFAULT_API&page=$page&per_page=$PER_PAGE"