package com.techmania.weatherproject.presentation.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.techmania.weatherproject.common.Constants.NOTIFICATION_EXTRA_MESSAGE
import com.techmania.weatherproject.common.Constants.NOTIFICATION_EXTRA_TITLE
import com.techmania.weatherproject.domain.models.AlarmItem
import com.techmania.weatherproject.domain.models.AlarmScheduler
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    private val pendingIntents = mutableSetOf<PendingIntent>()

    override fun schedule(item: AlarmItem) {
        cancelAllAlarms()

        val extras = Bundle().apply {
            putString(NOTIFICATION_EXTRA_MESSAGE, item.message)
            putString(NOTIFICATION_EXTRA_TITLE, item.title)
        }

        val intent = Intent(context, AlarmReceiver::class.java).putExtras(extras)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntents.add(pendingIntent)

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            item.time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Log.d(
            "AlarmScheduler",
            "Alarm scheduled for ${item.time}, title: ${item.title}, message: ${item.message}"
        )
        pendingIntents.forEach {
            Log.d("AlarmScheduler", "Pending intent: $it")
        }
    }

    override fun cancel(item: AlarmItem) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.hashCode(),
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntents.remove(pendingIntent)
    }

    override fun cancelAllAlarms() {
        pendingIntents.forEach { alarmManager.cancel(it) }
        pendingIntents.clear()
    }

}