package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Category {
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("pr_id")
    @Expose
    var prId = 0

    @SerializedName("name")
    @Expose
    var name = ""

    @SerializedName("description")
    @Expose
    var description: Any? = null

    @SerializedName("level")
    @Expose
    var level = 0

    @SerializedName("status")
    @Expose
    var status = 0

    @SerializedName("slug")
    @Expose
    var slug: Any? = null

    @SerializedName("created_at")
    @Expose
    var createdAt = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt = ""

    @SerializedName("type")
    @Expose
    var type = -1

    @SerializedName("group_id")
    @Expose
    var groupId = 0

    @SerializedName("book_id")
    @Expose
    var bookId = 0

    @SerializedName("title")
    @Expose
    var title = ""

    @SerializedName("info")
    @Expose
    var info: Any? = null

    @SerializedName("link")
    @Expose
    var link: Any? = null

    @SerializedName("image")
    @Expose
    var image = ""

    @SerializedName("deleted")
    @Expose
    var deleted = 0

    @SerializedName("sort")
    @Expose
    var sort = 0

    @SerializedName("book")
    @Expose
    var listBook = mutableListOf<Book>()

    @SerializedName("new")
    @Expose
    var listNews = mutableListOf<News>()

    @SerializedName("podcast")
    @Expose
    var listPodcast = mutableListOf<Podcast>()

    var listCategoryHome = mutableListOf<Category>()
}