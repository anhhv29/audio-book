package vn.gotech.audiobook.base.domain.api

import io.reactivex.Single
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

        @GET("api/home2")
        suspend fun getHome2(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<MutableList<Category>>>

        @GET("api/category-list")
        suspend fun getCategoryList(): Response<BaseResponse<MutableList<Category>>>

        @GET("api/category-book/{id}")
        suspend fun getCategoryBook(@Path("id") id: Int): Response<BaseResponse<MutableList<Book>>>

        @GET("api/book/{id}")
        suspend fun getBookDetail(@Path("id") id: Int): Response<BaseResponse<Book>>

        @GET("api/search-book")
        suspend fun getSearchBook(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<MutableList<Category>>>
    }

    interface Auth {
        @GET("api/get-cat-favorite")
        suspend fun getCateFavorite(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<MutableList<Category>>>

        @POST("api/set-cat-favorite")
        suspend fun setCateFavorite(@Body body: RequestBody): Response<BaseResponse<Any>>

        @GET("api/my-book-category")
        suspend fun getCategoryFavorite(): Response<BaseResponse<MutableList<Category>>>

        @GET("api/book-listening-favorite")
        suspend fun getFavoriteListening(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<MutableList<Book>>>

        @GET("api/category-slider")
        fun getCategorySlider(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<MutableList<Category>>>

        @GET("api/get-comment")

        @POST("api/new-comment")
        suspend fun newComment(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<Any>>

        @POST("api/book-favorite")
        suspend fun favoriteBook(@Body body: RequestBody): Response<BaseResponse<MutableList<Any>>>

        @POST("api/book-vote")
        suspend fun voteBook(@Body body: RequestBody): Response<BaseResponse<MutableList<Any>>>

        @POST("api/book-history")
        suspend fun historyBook(@Body body: RequestBody): Response<BaseResponse<Book>>

        @GET("api/get-book-suggestions")
        suspend fun getBookSuggest(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<MutableList<Book>>>

        @GET("api/auth/get-user")
        suspend fun getUserInfo(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<User>>

        @PUT("api/auth/update-user")
        suspend fun saveUserInfo(@QueryMap params: MutableMap<String, Any>): Response<BaseResponse<Any>>
    }
}
