package tech.thdev.flickr.network

import tech.thdev.FlickrApp
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.network.util.createRetrofit
import tech.thdev.flickr.util.isOnline

object RetrofitCreate {

    val githubApi: FlickrApi by lazy {
        createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            FlickrApp.context.isOnline()
        }
    }
}