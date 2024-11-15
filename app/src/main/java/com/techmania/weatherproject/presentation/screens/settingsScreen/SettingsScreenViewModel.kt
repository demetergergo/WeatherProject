package com.techmania.weatherproject.presentation.screens.settingsScreen

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.techmania.weatherproject.domain.models.AlarmItem
import com.techmania.weatherproject.domain.models.AlarmScheduler
import com.techmania.weatherproject.presentation.dataStore.MyDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import javax.inject.Inject


@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
) : ViewModel() {
    var isDarkMode = MutableStateFlow<Boolean>(false)
    var showBottomSheet = mutableStateOf(false)

    val permissionDialogs = MutableStateFlow<Boolean>(false)
    var notificationSwitchState = MutableStateFlow<Boolean>(false)
    var calendarState = mutableStateOf(false)

    val currentTime = Calendar.getInstance()

    var savedAlarmTime = MutableStateFlow<String>("--")


    suspend fun toggleTheme(context: Context) {
        MyDataStore.save("darkMode", (!isDarkMode.value).toString(), context)
        isDarkMode.value = MyDataStore.load("darkMode", context).toBoolean()
    }

    suspend fun loadSettings(context: Context) {
        isDarkMode.value = MyDataStore.load("darkMode", context).toBoolean()
        notificationSwitchState.value = MyDataStore.load("notifications", context).toBoolean()
        savedAlarmTime.value = MyDataStore.load("alarmTime", context) ?: "--"
    }

    fun toggleCalendarState() {
        calendarState.value = !calendarState.value
    }

    suspend fun toggleNotificationSwitchState(context: Context, scheduler: AlarmScheduler) {
        toggleNotificationSwitchState(context)
        if (!notificationSwitchState.value) {
            scheduler.cancelAllAlarms()
            savedAlarmTime.value = "-"
            saveAlarmTime(savedAlarmTime.value, context)
        }
    }

    private suspend fun toggleNotificationSwitchState(context: Context) {
        MyDataStore.save("notifications", (!notificationSwitchState.value).toString(), context)
        notificationSwitchState.value = MyDataStore.load("notifications", context).toBoolean()
    }

    fun togglePermissionDialogs() {
        permissionDialogs.value = !permissionDialogs.value
    }

    suspend fun confirmAlarm(
        hour: Int,
        minute: Int,
        scheduler: AlarmScheduler,
        context: Context,
    ) {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val alarmTime = LocalDateTime.of(
            LocalDate.now(),
            LocalTime.parse(LocalTime.of(hour, minute).toString(), timeFormatter)
        )
        saveAlarmTime(timeFormatter.format(alarmTime), context)
        val alarmItem = AlarmItem(
            time = alarmTime, message = "mock message",
            title = "mock title"
        )
        alarmItem.let(scheduler::schedule)
    }

    private suspend fun saveAlarmTime(alarmTime: String, context: Context) {
        MyDataStore.save("alarmTime", alarmTime, context)
        savedAlarmTime.value = alarmTime
    }
}
