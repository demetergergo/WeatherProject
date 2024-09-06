package com.techmania.weatherproject.data.networking.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoCurrentDto(
    @Json(name = "time") val time: String,
    @Json(name = "temperature_2m") val temperature_2m: Double,
    @Json(name = "apparent_temperature") val apparent_temperature: Double,
    @Json(name = "precipitation") val precipitation: Double,
    @Json(name = "weather_code") val weather_code: Int,
    @Json(name = "wind_speed_10m") val wind_speed_10m: Double,
)