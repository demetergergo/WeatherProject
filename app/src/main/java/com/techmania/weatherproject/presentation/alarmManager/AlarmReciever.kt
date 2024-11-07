package com.techmania.weatherproject.presentation.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.techmania.weatherproject.common.Constants.NOTIFICATION_EXTRA_MESSAGE
import com.techmania.weatherproject.common.Constants.NOTIFICATION_EXTRA_TITLE

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(NOTIFICATION_EXTRA_MESSAGE) ?: return
        val title = intent.getStringExtra(NOTIFICATION_EXTRA_TITLE) ?: return
        println("Alarm triggered: $message")
        Log.d("AlarmScheduler", "Alarm triggered for title: ${title}, message: $message")
        if (context != null) {
            showNotification(context, message, title)
        }
    }
}