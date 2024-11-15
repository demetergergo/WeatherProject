package com.techmania.weatherproject.presentation.alarmManager

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.techmania.weatherproject.R
import com.techmania.weatherproject.common.Constants.NOTIFICATION_CHANNEL_ID

fun showNotification(context: Context, message: String, title: String) {
    try {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentText(message)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.weather_app)
            .build()
        notificationManager.notify(1, notification)
    } catch (e: Exception) {
        Log.e("AlarmReceiver", "Error during notification creation: ", e)
    }
}