package tech.thdev.flickr.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DefaultPhotoResponse(
        @SerializedName("photos") val photos: DefaultPhotos,
        @SerializedName("stat") val status: String,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String) : Parcelable

@Parcelize
data class DefaultPhotos(
        @SerializedName("page") val page: Int = 0,
        @SerializedName("pages") val pages: Int = 0,
        @SerializedName("perpage") val perpage: Int = 0,
        @SerializedName("photo") val photo: List<DefaultPhoto> = mutableListOf(),
        @SerializedName("total") val total: Int = 0) : Parcelable

@Parcelize
data class DefaultPhoto(
        @SerializedName("farm") private val farmId: Int,
        @SerializedName("id") val photoId: String,
        @SerializedName("isFamily") val isFamily: Int,
        @SerializedName("isfriend") val isFriend: Int,
        @SerializedName("ispublic") val isPublic: Int,
        @SerializedName("owner") val owner: String,
        @SerializedName("secret") val secret: String,
        @SerializedName("server") private val serverId: String,
        @SerializedName("title") val title: String,
        private var _imageUrl: String? = "") : Parcelable {

    /**
     * https://www.flickr.com/services/api/misc.urls.html
     * Image url 조합
     */
    val imageUrl: String
        get() {
            if (_imageUrl == null) {
                _imageUrl = "https://farm$farmId.staticflickr.com/$serverId/${photoId}_$secret.jpg"
            }
            return _imageUrl!!
        }
}