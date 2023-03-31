package vn.gotech.audiobook.base.domain

open class BaseErrorSignal(
        var errorCode: Int = 0,
        var errorMessage: String = ""
) {

    companion object {
        const val RESPONSE_SUCCESS = 0
        const val ERROR_HAS_MESSAGE = 0
        const val ERROR_AUTH = 401
        const val ERROR_SERVER = 500
        const val ERROR_NETWORK = 101
        const val ERROR_UNKNOWN = 103
        const val ERROR_PARSE = -500

        const val ERROR_1 = 1
        const val ERROR_401 = 401
        const val ERROR_100 = 100
    }
}