package tech.thdev.flickr.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FlickrPhoto(
        @SerializedName("farm") val farm: Int,
        @SerializedName("id") val id: String,
        @SerializedName("isFamily") val isFamily: Int,
        @SerializedName("isfriend") val isFriend: Int,
        @SerializedName("ispublic") val isPublic: Int,
        @SerializedName("owner") val owner: String,
        @SerializedName("secret") val secret: String,
        @SerializedName("server") val server: String,
        @SerializedName("title") val title: String,
        private var _imageUrl: String? = "") : Parcelable {

    val imageUrl: String
        get() {
            if (_imageUrl == null) {
                _imageUrl = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"
            }
            return _imageUrl!!
        }
}