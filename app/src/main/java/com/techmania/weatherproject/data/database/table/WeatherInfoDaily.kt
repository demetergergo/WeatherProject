package com.techmania.weatherproject.data.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.techmania.weatherproject.data.sharedDataComponents.IWeatherInfo

@Entity
data class WeatherInfoDaily(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "time") override val time: String,
    @ColumnInfo(name = "temperature_2m") override val temperature_2m: Double,
    @ColumnInfo(name = "apparent_temperature") override val apparent_temperature: Double,
    @ColumnInfo(name = "precipitation") override val precipitation: Double,
    @ColumnInfo(name = "weather_code") override val weather_code: Int,
    @ColumnInfo(name = "wind_speed_10m") override val wind_speed_10m: Double,
) : IWeatherInfo
