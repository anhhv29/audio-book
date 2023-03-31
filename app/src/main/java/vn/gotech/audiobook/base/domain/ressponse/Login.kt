package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Login {
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("token_type")
    @Expose
    var tokenType: String? = null

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

}