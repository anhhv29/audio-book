package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {

    @SerializedName("code")
    @Expose
    var code: Int = 0

    @SerializedName("data")
    @Expose
    var data: T? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

}