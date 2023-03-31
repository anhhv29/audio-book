package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Book {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("category_id")
    @Expose
    var categoryId = 0

    @SerializedName("name", alternate = ["title"])
    @Expose
    var title = ""

    @SerializedName("slug")
    @Expose
    var slug: Any? = null

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Any? = null

    @SerializedName("image")
    @Expose
    var image: Any? = null

    @SerializedName("audio_link")
    @Expose
    var audioLink = ""

    @SerializedName("description")
    @Expose
    var description = ""

    @SerializedName("content")
    @Expose
    var content = ""

    @SerializedName("created_at")
    @Expose
    var createdAt = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt = ""

    @SerializedName("author")
    @Expose
    var author = ""

    @SerializedName("director")
    @Expose
    var director = ""

    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("duration")
    @Expose
    var duration: String? = null

    @SerializedName("catalog")
    @Expose
    var catalog = ""

    @SerializedName("rate")
    @Expose
    var rate = 0

    @SerializedName("favorite")
    @Expose
    var favorite = 0

    @SerializedName("user_star")
    @Expose
    var userStar = 0

    @SerializedName("view_count")
    @Expose
    var viewCount = 0

    @SerializedName("last_chapter")
    @Expose
    var lastChapter = 0

    @SerializedName("type")
    @Expose
    var type = ""

    @SerializedName("pivot")
    @Expose
    var pivot: Pivot? = null

    @SerializedName("is_vip")
    @Expose
    var isVip = 0

    @SerializedName("chapter")
    @Expose
    var chapter: MutableList<Chapter> = mutableListOf()

    @SerializedName("category")
    @Expose
    var category: MutableList<Category> = mutableListOf()
}