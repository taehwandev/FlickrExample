package tech.thdev.flickr.network

import retrofit2.http.GET
import retrofit2.http.Query
import tech.thdev.flickr.BuildConfig
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.data.PhotoDetail

interface FlickrApi {

    //https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=4a88c79b8b25ddfcf8f06d78ee5d25e8&per_page=20&page=25&format=json&nojsoncallback=1
    @GET("?method=flickr.interestingness.getList&format=json&nojsoncallback=1")
    suspend fun loadFlickrDefault(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_API_KEY
    ): DefaultPhotoResponse

    // https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=06486cf47869200a22d27c4d21d48c09&photo_id=44437878270&format=json&nojsoncallback=1&api_sig=7969880c1464e9854d1b23cf206289f7
    @GET("?method=flickr.photos.getInfo&format=json&nojsoncallback=1")
    suspend fun loadPhotoDetail(
        @Query("photo_id") photoId: String,
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_API_KEY
    ): PhotoDetail
}

