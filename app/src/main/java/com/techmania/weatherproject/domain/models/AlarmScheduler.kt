package com.techmania.weatherproject.domain.models

//TODO: REMOVE DEPENDENCY TO DOMAIN
interface AlarmScheduler {
    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
    fun cancelAllAlarms()
}