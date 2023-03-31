package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Episode {
    @SerializedName("title")
    @Expose
    var title: List<Any>? = null

    @SerializedName("description")
    @Expose
    var description: List<Any>? = null

    @SerializedName("link")
    @Expose
    var link: String? = null

    @SerializedName("guid")
    @Expose
    var guid: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("pubDate")
    @Expose
    var pubDate: String? = null

//    @SerializedName("enclosure")
//    @Expose
//    var enclosure: Enclosure? = null
}