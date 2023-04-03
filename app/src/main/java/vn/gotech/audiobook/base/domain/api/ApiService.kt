package vn.gotech.audiobook.base.domain.api

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import vn.gotech.audiobook.base.domain.ressponse.*

class ApiService {

    interface NoAuth {
        @POST("api/auth/login-new")
        suspend fun loginForPassword(@Body body: RequestBody): Response<BaseResponse<Login>>

        @POST("api/auth/register-new")
        suspend fun registerForPass(@Body body: RequestBody): Response<BaseResponse<Any>>

        @POST("api/auth/forget-password")
        suspend fun forgetPassword(@Body body: RequestBody): Response<BaseResponse<Any>>

        @POST("api/auth/auth-otp")
        suspend fun registerOtp(@Body body: RequestBody): Response<BaseResponse<Login>>

        @POST("api/auth/auth-forget-password")
        suspend fun forgetOtp(@Body body: RequestBody): Response<BaseResponse<Login>>

        @POST("api/auth/change-password")
        suspend fun changePassword(@Body body: RequestBody): Response<BaseResponse<Any>>

        @POST("api/auth/auth-change-password")
        suspend fun changePasswordOtp(@Body body: RequestBody): Response<BaseResponse<Login>>

        @GET("api/category-list")
        suspend fun getCategoryList(): Response<BaseResponse<MutableList<Category>>>

        @GET("api/category-book/{id}")
        suspend fun getCategoryBook(@Path("id") id: Int): Response<BaseResponse<MutableList<Book>>>
    }

    interface Auth {
        @GET("api/auth/get-user")
        suspend fun getUserInfo(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<User>>

        @PUT("api/auth/update-user")
        suspend fun saveUserInfo(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<Any>>
    }
}
