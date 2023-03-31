package vn.gotech.audiobook.services

import android.content.Context
import android.os.Bundle
import android.util.Log

object NotificationUtils {

    fun resolveNotification(context: Context, extras: Bundle) {
        Log.d("NotificationUtils", "notification:  ${extras}")
        val type = extras.getString("type")
    }
}