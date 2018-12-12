package tech.thdev.flickr.data

import com.google.gson.annotations.SerializedName

data class PhotoDetail(
        @SerializedName("photo") val photo: Photo,
        @SerializedName("stat") val stat: String,
        @SerializedName("message") val message: String = "")

data class Photo(
        @SerializedName("comments") val comments: Comments,
        @SerializedName("dates") val dates: Dates,
        @SerializedName("dateuploaded") val dateUploaded: String,
        @SerializedName("description") val description: Description,
        @SerializedName("farm") val farmId: Int,
        @SerializedName("id") val photoId: String,
        @SerializedName("isfavorite") val isFavorite: Int,
        @SerializedName("license") val license: String,
        @SerializedName("media") val media: String,
        @SerializedName("owner") val owner: Owner,
        @SerializedName("people") val people: People,
        @SerializedName("publiceditability") val publiceditability: Publiceditability,
        @SerializedName("rotation") val rotation: Int,
        @SerializedName("safety_level") val safetyLevel: String,
        @SerializedName("secret") val secret: String,
        @SerializedName("server") val serverId: String,
        @SerializedName("tags") val tags: Tags,
        @SerializedName("title") val title: Title,
        @SerializedName("urls") val urls: Urls,
        @SerializedName("usage") val usage: Usage,
        @SerializedName("views") val views: String,
        @SerializedName("visibility") val visibility: Visibility,
        private var _imageUrl: String? = null) {


    /**
     * https://www.flickr.com/services/api/misc.urls.html
     * Image url 조합
     */
    val imageUrl: String
        get() = "$defaultImageUrl.jpg"

    val imageUrlLarge: String
        get() = "${defaultImageUrl}_b.jpg"

    private val defaultImageUrl: String
        get() {
            if (_imageUrl == null) {
                _imageUrl = "https://farm$farmId.staticflickr.com/$serverId/${photoId}_$secret"
            }
            return _imageUrl!!
        }
}

data class Urls(
        @SerializedName("url") val url: List<Url>
)

data class Url(
        @SerializedName("_content") val content: String,
        @SerializedName("type") val type: String
)

data class Comments(
        @SerializedName("_content") val content: String
)

data class Tags(
        @SerializedName("tag") val tag: List<Tag>
)

data class Tag(
        @SerializedName("_content") val content: String,
        @SerializedName("author") val author: String,
        @SerializedName("authorname") val authorName: String,
        @SerializedName("id") val id: String,
        @SerializedName("machine_tag") val machineTag: Int,
        @SerializedName("raw") val raw: String
)

data class Title(
        @SerializedName("_content") val content: String
)

data class Visibility(
        @SerializedName("isfamily") val isFamily: Int,
        @SerializedName("isfriend") val isFriend: Int,
        @SerializedName("ispublic") val isPublic: Int
)

data class Publiceditability(
        @SerializedName("canaddmeta") val canaddmeta: Int,
        @SerializedName("cancomment") val cancomment: Int
)

data class Owner(
        @SerializedName("iconfarm") val iconfarm: Int,
        @SerializedName("iconserver") val iconServer: String,
        @SerializedName("location") val location: String,
        @SerializedName("nsid") val nsid: String,
        @SerializedName("path_alias") val pathAlias: Any,
        @SerializedName("realname") val realName: String,
        @SerializedName("username") val username: String
)

data class Dates(
        @SerializedName("lastupdate") val lastupdate: String,
        @SerializedName("posted") val posted: String,
        @SerializedName("taken") val taken: String,
        @SerializedName("takengranularity") val takengranularity: String,
        @SerializedName("takenunknown") val takenunknown: String
)

data class Description(
        @SerializedName("_content") val content: String
)

data class People(
        @SerializedName("haspeople") val haspeople: Int
)

data class Usage(
        @SerializedName("canblog") val canblog: Int,
        @SerializedName("candownload") val candownload: Int,
        @SerializedName("canprint") val canprint: Int,
        @SerializedName("canshare") val canshare: Int
)