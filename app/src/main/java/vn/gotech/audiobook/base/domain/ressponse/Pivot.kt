package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Pivot {
    @SerializedName("slider_id")
    @Expose
    var sliderId = 0

    @SerializedName("book_id")
    @Expose
    var bookId = 0
}