package com.techmania.weatherproject.domain.models

interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
    fun cancelAllAlarms()
}