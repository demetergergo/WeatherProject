package com.techmania.weatherproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techmania.weatherproject.data.database.dao.WeatherInfoCurrentDao
import com.techmania.weatherproject.data.database.dao.WeatherInfoDailyDao
import com.techmania.weatherproject.data.database.dao.WeatherInfoHourlyDao
import com.techmania.weatherproject.data.database.table.WeatherInfo
import com.techmania.weatherproject.data.database.table.WeatherInfoCurrent
import com.techmania.weatherproject.data.database.table.WeatherInfoDaily

@Database(
    entities = [WeatherInfoCurrent::class, WeatherInfoDaily::class, WeatherInfo::class],
    version = 1
)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract val weatherInfoCurrentDao: WeatherInfoCurrentDao
    abstract val weatherInfoDailyDao: WeatherInfoDailyDao
    abstract val weatherInfoHourlyDao: WeatherInfoHourlyDao
}