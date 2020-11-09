package tech.thdev.flickr.network

import tech.thdev.FlickrApp
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.network.util.createRetrofit
import tech.thdev.flickr.util.isOnline

/**
 * 2편 샘플 코드 - Property와 function 정의에서 알아두면 좋은 것
 */
object RetrofitCreate {

    /**
     * flickrApi를 lazy하게 초기화하는 Property 정의는 잘 맞을까?
     */
    val flickrApi: FlickrApi by lazy {
        createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            FlickrApp.context.isOnline()
        }
    }
}