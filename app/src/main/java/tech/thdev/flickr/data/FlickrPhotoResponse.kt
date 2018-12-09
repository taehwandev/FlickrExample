package tech.thdev.flickr.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrPhotoResponse(
        @SerializedName("photos") val photos: FlickrPhotos,
        @SerializedName("stat") val status: String) : Parcelable