package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Chapter {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("title")
    @Expose
    var title = ""

    @SerializedName("slug")
    @Expose
    var slug: Any? = null

    @SerializedName("audio_link")
    @Expose
    var audioLink = ""

    @SerializedName("description")
    @Expose
    var description = ""

    @SerializedName("content")
    @Expose
    var content: Any? = null

    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("created_at")
    @Expose
    var createdAt = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt = ""

    @SerializedName("book_id")
    @Expose
    var bookId = 0

    @SerializedName("duration")
    @Expose
    var duration = ""
}