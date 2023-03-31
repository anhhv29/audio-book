package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Podcast {
    @SerializedName("id")
    @Expose
    var id = 0
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("image")
    @Expose
    var images: String? = null
//
//    @SerializedName("item")
//    @Expose
//    var listEpisode: MutableList<Episode>? = null
}