package com.techmania.weatherproject.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techmania.weatherproject.data.database.table.WeatherInfoDaily

@Dao
interface WeatherInfoDailyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(infos: List<WeatherInfoDaily>)

    @Query("DELETE FROM weatherinfodaily")
    fun nukeAll()

    @Query("SELECT * FROM weatherinfodaily")
    fun getAll(): List<WeatherInfoDaily>
}