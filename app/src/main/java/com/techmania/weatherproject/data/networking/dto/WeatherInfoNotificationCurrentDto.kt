package com.techmania.weatherproject.data.networking.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoNotificationCurrentDto(
    @Json(name = "temperature_2m") val temperature_2m: Double,
    @Json(name = "weather_code") val weather_code: Int,
)
