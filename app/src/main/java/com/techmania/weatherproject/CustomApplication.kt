package com.techmania.weatherproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.techmania.weatherproject.common.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            Constants.NOTIFICATION_CHANNEL,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = ""

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}