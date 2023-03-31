package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Home {
    @SerializedName("id")
    @Expose
    var id: Long = -1L

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("type")
    @Expose
    var type: Int = 0

    @SerializedName("data")
    @Expose
    var listData = mutableListOf<Card>()
}