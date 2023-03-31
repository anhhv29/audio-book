package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class News {
    @SerializedName("id")
    @Expose
    var id: Long = -1L
    @SerializedName("title")
    @Expose
    var name: String? = null
    @SerializedName("view_count")
    @Expose
    var viewCount: Int = 0
    @SerializedName("rating")
    @Expose
    var rating: Float = 0.0f
    @SerializedName("isRead")
    @Expose
    var isRead: Int = 0
    @SerializedName("favorite")
    @Expose
    var favorite: Int = 0
    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Any? = null
    @SerializedName("audio_link")
    @Expose
    var audioLink: String? = null
}