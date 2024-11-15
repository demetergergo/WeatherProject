package com.techmania.weatherproject.presentation.alarmManager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.techmania.weatherproject.R
import com.techmania.weatherproject.usecases.FetchWeatherInfoForNotificationUseCase
import com.techmania.weatherproject.usecases.ObserveLocationInfoUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmHelper: AlarmHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarm received")
        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                alarmHelper.handleAlarm(context)
            } catch (e: Exception) {
                Log.e("AlarmReceiver", "Error during weather request", e)
            } finally {
                pendingResult.finish()
            }
        }
    }
}


class AlarmHelper @Inject constructor(
    private val fetchWeatherInfoForNotificationUseCase: FetchWeatherInfoForNotificationUseCase,
    private val observeLocationInfoUseCase: ObserveLocationInfoUseCase,
) {
    suspend fun handleAlarm(context: Context?) {
        val currentLocation = observeLocationInfoUseCase()
        val weatherInfo = fetchWeatherInfoForNotificationUseCase(
            currentLocation!!.latitude,
            currentLocation.longitude
        )
        if (context != null) {
            val message =
                "${context.getString(R.string.current_temperature)}${weatherInfo.temperature} ${
                    context.getString(R.string.unit_celsius)
                }"
            val title = context.getString(R.string.daily_notification)
            //TODO: adding either of these lines to the strings will delay the notification by minutes for some reason
            // context.getString(weatherInfo.weatherDesc)
            // context.getString(weatherInfo.iconRes)
            withContext(Dispatchers.Main) {
                showNotification(context, message, title)
            }
        }
    }
}