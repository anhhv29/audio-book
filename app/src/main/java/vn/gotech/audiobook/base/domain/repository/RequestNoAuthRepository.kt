package vn.gotech.audiobook.base.domain.repository

import retrofit2.http.Path
import vn.gotech.audiobook.base.domain.api.ApiService

class RequestNoAuthRepository(private val apiService: ApiService.NoAuth) {
    suspend fun getCategoryList() = apiService.getCategoryList()
    suspend fun getCategoryBook(@Path("id") id: Int) = apiService.getCategoryBook(id)
}