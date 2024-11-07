package com.techmania.weatherproject.presentation.alarmManager

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.techmania.weatherproject.R
import com.techmania.weatherproject.common.Constants.NOTIFICATION_CHANNEL_ID

fun showNotification(context: Context, message: String, title: String) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setContentText(message)
        .setContentTitle(title)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()
    notificationManager.notify(1, notification)
}