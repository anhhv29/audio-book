package vn.gotech.audiobook.base.domain.repository

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.QueryMap
import vn.gotech.audiobook.base.domain.api.ApiService

class RequestNoAuthRepository(private val apiService: ApiService.NoAuth) {
    suspend fun login(@Body body: RequestBody) = apiService.loginForPassword(body)
    suspend fun registerForPass(@Body body: RequestBody) = apiService.registerForPass(body)
    suspend fun forgetPassword(@Body body: RequestBody) = apiService.forgetPassword(body)
    suspend fun registerOtp(@Body body: RequestBody) = apiService.registerOtp(body)
    suspend fun forgetOtp(@Body body: RequestBody) = apiService.forgetOtp(body)
    suspend fun changePassword(@Body body: RequestBody) = apiService.changePassword(body)
    suspend fun changePasswordOtp(@Body body: RequestBody) = apiService.changePasswordOtp(body)
    suspend fun getHome2(@QueryMap params: MutableMap<String, Any>) = apiService.getHome2(params)
    suspend fun getCategoryList() = apiService.getCategoryList()
    suspend fun getCategoryBook(@Path("id") id: Int) = apiService.getCategoryBook(id)
    suspend fun getBookDetail(@Path("id") id: Int) = apiService.getBookDetail(id)
    suspend fun getSearchBook(@QueryMap params: MutableMap<String, Any>) = apiService.getSearchBook(params)
}