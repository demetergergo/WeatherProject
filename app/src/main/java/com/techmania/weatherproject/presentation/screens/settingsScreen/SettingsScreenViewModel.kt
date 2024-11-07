package com.techmania.weatherproject.presentation.screens.settingsScreen

import android.content.Context
import androidx.compose.runtime.MutableState
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
class SettingsScreenViewModel @Inject constructor() : ViewModel() {
    var isDarkMode = MutableStateFlow<Boolean>(false)
    var isBackButtonEnabled = mutableStateOf(true)
    var showBottomSheet = mutableStateOf(false)

    //todo: implement permission handling
    val hasNotificationPermission = mutableStateOf(false)
    var notificationSwitchState = MutableStateFlow<Boolean>(false)
    var calendarState = mutableStateOf(false)

    val currentTime = Calendar.getInstance()

    var secondsText = mutableStateOf("")
    val alarmItem = mutableStateOf<AlarmItem?>(null)



    suspend fun toggleTheme(context: Context) {
        MyDataStore.save("darkMode", (!isDarkMode.value).toString(), context)
        isDarkMode.value = MyDataStore.load("darkMode", context).toBoolean()
    }

    suspend fun loadSettings(context: Context) {
        isDarkMode.value = MyDataStore.load("darkMode", context).toBoolean()
        notificationSwitchState.value = MyDataStore.load("notifications", context).toBoolean()
    }

    fun toggleCalendarState() {
        calendarState.value = !calendarState.value
    }

    suspend fun toggleNotificationSwitchState(context: Context) {
        MyDataStore.save("notifications", (!notificationSwitchState.value).toString(), context)
        notificationSwitchState.value = MyDataStore.load("notifications", context).toBoolean()
    }

    fun confirmAlarm(
        secondsText: MutableState<String>,
        hour: Int,
        minute: Int,
        scheduler: AlarmScheduler,
    ) {
        secondsText.value = LocalTime.of(hour, minute).toString()
        alarmItem.value = AlarmItem(
            time = LocalDateTime.of(
                LocalDate.now(),
                LocalTime.parse(secondsText.value, DateTimeFormatter.ofPattern("HH:mm"))
            ), message = "mock message",
            title = "mock title"
        )
        alarmItem.value!!.let(scheduler::schedule)
    }
}
