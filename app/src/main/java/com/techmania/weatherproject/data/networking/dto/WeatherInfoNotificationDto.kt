package com.techmania.weatherproject.data.networking.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoNotificationDto(
    @Json(name = "current") val current: WeatherInfoNotificationCurrentDto,
)