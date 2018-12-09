package tech.thdev.flickr.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrPhotos(
        @SerializedName("page") val page: Int = 0,
        @SerializedName("pages") val pages: Int = 0,
        @SerializedName("perpage") val perpage: Int = 0,
        @SerializedName("photo") val photo: List<FlickrPhoto> = mutableListOf(),
        @SerializedName("total") val total: Int = 0) : Parcelable