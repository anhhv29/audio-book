package vn.gotech.audiobook.base.domain.repository

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.QueryMap
import vn.gotech.audiobook.base.domain.api.ApiService

class RequestAuthRepository(private val apiService: ApiService.Auth) {
    suspend fun getCateFavorite(@QueryMap params: MutableMap<String, Any>) =
        apiService.getCateFavorite(params)

    suspend fun setCateFavorite(@Body body: RequestBody) = apiService.setCateFavorite(body)
    suspend fun getCategoryFavorite() = apiService.getCategoryFavorite()
    suspend fun getFavoriteListening(@QueryMap params: MutableMap<String, Any>) =
        apiService.getFavoriteListening(params)

    suspend fun favoriteBook(@Body body: RequestBody) = apiService.favoriteBook(body)
    suspend fun getBookSuggest(@QueryMap params: MutableMap<String, Any>) =
        apiService.getBookSuggest(params)

    suspend fun getProfiles(@QueryMap params: MutableMap<String, Any>) =
        apiService.getUserInfo(params)

    suspend fun updateUserInfo(@QueryMap params: MutableMap<String, Any>) =
        apiService.saveUserInfo(params)

    suspend fun getComment(@QueryMap params: MutableMap<String, Any>) =
        apiService.getComment(params)

    suspend fun newComment(@QueryMap params: MutableMap<String, Any>) =
        apiService.newComment(params)

    suspend fun voteBook(@Body body: RequestBody) = apiService.voteBook(body)
    suspend fun historyBook(@Body body: RequestBody) = apiService.historyBook(body)
    suspend fun getCategorySlider(@QueryMap params: MutableMap<String, Any>) =
        apiService.getCategorySlider(params)
}