package vn.gotech.audiobook.base.common

import  vn.gotech.audiobook.BuildConfig

object Const {
    var webViewCSS: String? = null
    const val PAGE_LIMIT = 20
    const val BASE_URL = "http://books.gotech.vn/"

    object API {
        const val TIME_OUT = 60L
    }

    object TransferKey {
        const val EXTRA_JSON = "json"
        const val EXTRA_DATA_BOOK = "data_books"
        const val EXTRA_DATA_PODCAST = "data_podcast"
        const val EXTRA_ID = "id"
        const val EXTRA_STATUS = "status"
        const val EXTRA_LOADING = "loading"
        const val EXTRA_TITLE = "title"
        const val EXTRA_CONTENT = "content"
        const val EXTRA_NEWS = "news"
        const val EXTRA_PHONE = "phone"
        const val EXTRA_PASSWORD = "password"
        const val EXTRA_OLD_PASSWORD = "old_password"
        const val EXTRA_NAME = "name"
        const val EXTRA_ACTION = "action"
    }

    object ActionKey {
        const val ACTION_START_SERVICE = "${BuildConfig.APPLICATION_ID}.ACTION_START_SERVICE"
        const val ACTION_STOP_SERVICE = "${BuildConfig.APPLICATION_ID}.ACTION_STOP_SERVICE"
        const val ACTION_PLAY = "${BuildConfig.APPLICATION_ID}.ACTION_PLAY"
        const val ACTION_NEXT = "${BuildConfig.APPLICATION_ID}.ACTION_NEXT"
        const val ACTION_PREV = "${BuildConfig.APPLICATION_ID}.ACTION_PREV"
        const val ACTION_COMPLETE = "${BuildConfig.APPLICATION_ID}.ACTION_COMPLETE"
        const val ACTION_PAUSE = "${BuildConfig.APPLICATION_ID}.ACTION_PAUSE"
        const val ACTION_NO_VIP = "${BuildConfig.APPLICATION_ID}.ACTION_NO_VIP"
        const val ACTION_CHANGE_SONG = "${BuildConfig.APPLICATION_ID}.ACTION_CHANGE_SONG"
        const val ACTION_NEXT_15S = "${BuildConfig.APPLICATION_ID}.ACTION_NEXT_15S"
        const val ACTION_PREV_15S = "${BuildConfig.APPLICATION_ID}.ACTION_PREV_15S"
        const val ACTION_SET_TIMER = "${BuildConfig.APPLICATION_ID}.ACTION_SET_TIMER"
        const val ACTION_TIMER_SUCCESS = "${BuildConfig.APPLICATION_ID}.ACTION_TIMER_SUCCESS"
        const val ACTION_CANCEL_TIMER = "${BuildConfig.APPLICATION_ID}.ACTION_CANCEL_TIMER"
        const val ACTION_OPEN_BOOK = "${BuildConfig.APPLICATION_ID}.ACTION_OPEN_BOOK"
        const val ACTION_OPEN_BOOK_DETAIL = "${BuildConfig.APPLICATION_ID}.ACTION_OPEN_BOOK_DETAIL"
        const val ACTION_SHOW_BOOK_DETAIL = "${BuildConfig.APPLICATION_ID}.ACTION_SHOW_BOOK_DETAIL"
        const val ACTION_OPEN_CATEGORY_DETAIL = "${BuildConfig.APPLICATION_ID}.ACTION_OPEN_CATEGORY_DETAIL"
        const val ACTION_CLOSE_CATEGORY_DETAIL = "${BuildConfig.APPLICATION_ID}.ACTION_CLOSE_CATEGORY_DETAIL"
        const val ACTION_FAVORITE_BOOK = "${BuildConfig.APPLICATION_ID}.ACTION_FAVORITE_BOOK"
        const val ACTION_ALARM_TIMER = 0x0001
        const val ACTION_ALARM_CANCEL = 0x0002
        const val ACTION_NO_ACTION = 0x0003
        const val ACTION_STOP = 0x0004
        const val ACTION_COMPLETE_AUTO_NEXT = 0x0005
    }
}