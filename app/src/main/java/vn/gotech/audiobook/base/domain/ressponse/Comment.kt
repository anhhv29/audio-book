package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Comment {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("book_id")
    @Expose
    var bookId: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("user_id")
    @Expose
    var userId: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("user_name")
    @Expose
    var userName: String? = null
}