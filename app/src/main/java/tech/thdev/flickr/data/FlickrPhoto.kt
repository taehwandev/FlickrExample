package tech.thdev.flickr.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrPhoto(
        @SerializedName("farm") private val farmId: Int,
        @SerializedName("id") private val userId: String,
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
                _imageUrl = "https://farm$farmId.staticflickr.com/$serverId/${userId}_$secret.jpg"
            }
            return _imageUrl!!
        }
}