package com.techmania.weatherproject.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.techmania.weatherproject.data.database.table.WeatherInfo

@Dao
interface WeatherInfoHourlyDao {
    @Insert
    fun insertAll(infos: List<WeatherInfo>)

    @Query("DELETE FROM weatherinfo")
    fun nukeAll()

    @Query("SELECT * FROM weatherinfo")
    fun getAll(): List<WeatherInfo>
}