package vn.gotech.audiobook.base.domain.auth

import android.util.Log
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import vn.gotech.audiobook.base.common.UserDataManager
import vn.gotech.audiobook.base.domain.api.ApiService


class AppAuthenticator : Authenticator {

    companion object {
        private const val TAG = "AppAuthenticator"
    }

    lateinit var authService: ApiService.Auth

    override fun authenticate(route: Route?, response: Response): Request? {
        Log.d(TAG, "authen failed")

        response.let {
            if (responseCount(response) >= 2) {
                return null
            }
        }

        var obtained = false

        /**
         * Refresh token ở đây
         */


        return if (obtained) {
            response.request.newBuilder()
                .header("Authorization", "Bearer " + UserDataManager.accessToken)
                .build()
        } else
            null
    }

    private fun responseCount(response: Response): Int {
        var result = 1
        var failed: Response? = response
        while (failed?.priorResponse != null) {
            result++
            failed = failed.priorResponse
        }

        return result
    }

}