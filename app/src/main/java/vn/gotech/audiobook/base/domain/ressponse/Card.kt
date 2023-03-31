package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Card {
    @SerializedName("id")
    @Expose
    var id: Long = -1L
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("viewCount")
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
    @SerializedName("image")
    @Expose
    var image: String? = null
    @SerializedName("progress")
    @Expose
    var progress: Int = 0
}