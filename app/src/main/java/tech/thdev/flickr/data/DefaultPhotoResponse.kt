package tech.thdev.flickr.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * 2편 샘플 코드 - Property와 function 정의에서 알아두면 좋은 것
 */

@Parcelize
data class DefaultPhotoResponse(
    @SerializedName("photos") val photos: DefaultPhotos,
    @SerializedName("stat") val status: String,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String
) : Parcelable

@Parcelize
data class DefaultPhotos(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("perpage") val perpage: Int = 0,
    @SerializedName("photo") val photo: List<DefaultPhoto> = mutableListOf(),
    @SerializedName("total") val total: Int = 0
) : Parcelable

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
    private var _imageUrl: String? = ""
) : Parcelable {

    /**
     * https://www.flickr.com/services/api/misc.urls.html
     * Image url 조합
     *
     * 이미지를 표현하는 다음의 값은 조합을 할 뿐 다른 행동을 하지는 않는다. property로 정의해도 무관하다.
     */
    val imageUrl2: String
        get() {
            if (_imageUrl == null) {
                _imageUrl = "https://farm$farmId.staticflickr.com/$serverId/${photoId}_$secret.jpg"
            }
            return _imageUrl!!
        }

    /**
     * 이런식으로 다른 행동을 표현하는 경우가 생긴다면?
     */
    private var isUse = false
    val imageUrl: String
        get() = if (isUse.not()) {
            isUse = true
            "https://farm$farmId.staticflickr.com/$serverId/${photoId}_$secret.jpg"
        } else {
            "Not.jpg"
        }
}