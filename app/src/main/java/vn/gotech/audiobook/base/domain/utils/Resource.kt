package vn.gotech.audiobook.base.domain.utils

import android.util.Log

data class Resource<out T>(val status: Status, val code: Int?, val message: String?, val data: T?) {

    companion object {

        private const val TAG = "HttpResponse"

        fun <T> onSuccess(code: Int?, msg: String?, data: T?): Resource<T> {
//            Log.e(TAG, "onSuccess ===> code = $code")
//            Log.e(TAG, "onSuccess ===> data = " + Gson().toJson(data))
            return Resource(Status.SUCCESS, code, msg, data)
        }

        fun <T> onError(code: Int?, msg: String?): Resource<T> {
            Log.e(TAG, "onError ===> code = $code")
            Log.e(TAG, "onError ===> message = $msg")
            return Resource(Status.ERROR, code, msg, null)
        }

        fun <T> onLoading(): Resource<T> {
            return Resource(Status.LOADING, 0, null, null)
        }
    }

}