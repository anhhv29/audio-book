package vn.gotech.audiobook.base.domain.ressponse

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("email")
    @Expose
    var email: Any? = null

    @SerializedName("permissions")
    @Expose
    var permissions: Any? = null

    @SerializedName("last_login")
    @Expose
    var lastLogin: Any? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("deleted")
    @Expose
    var deleted: Int? = null

    @SerializedName("reset_password")
    @Expose
    var resetPassword: Any? = null

    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("imei")
    @Expose
    var imei: String? = null

    @SerializedName("is_check")
    @Expose
    var isCheck: Int? = null

    @SerializedName("package_id")
    @Expose
    var packageId: Int? = null

    @SerializedName("expired_time")
    @Expose
    var expiredTime: String? = null

    @SerializedName("key")
    @Expose
    var key: Any? = null
}