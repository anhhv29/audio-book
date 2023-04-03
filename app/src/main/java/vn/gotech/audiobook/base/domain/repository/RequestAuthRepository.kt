package vn.gotech.audiobook.base.domain.repository

import retrofit2.http.QueryMap
import vn.gotech.audiobook.base.domain.api.ApiService

class RequestAuthRepository(private val apiService: ApiService.Auth) {
    suspend fun getProfiles(@QueryMap params: MutableMap<String, Any>) =
        apiService.getUserInfo(params)

    suspend fun updateUserInfo(@QueryMap params: MutableMap<String, Any>) =
        apiService.saveUserInfo(params)
}