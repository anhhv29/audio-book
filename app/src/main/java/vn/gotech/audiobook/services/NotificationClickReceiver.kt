package vn.gotech.audiobook.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class NotificationClickReceiver : BroadcastReceiver() {

    val TAG = "NotificationClick"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: " + intent.extras!!)

        val extras = intent.extras
        val keys = extras!!.keySet()
        for (key in keys) {
            val value = extras.get(key)
            Log.d(TAG, "onReceive: $key: $value")
        }

        NotificationUtils.resolveNotification(context, extras ?: Bundle())
    }
}