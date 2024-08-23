package com.techmania.weatherproject.data.networking.Dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoListDto(
    @Json(name = "hourly") val hourly: WeatherInfoDto,
    @Json(name = "current") val current: WeatherInfoCurrentDto,
    @Json(name = "daily") val daily: WeatherInfoDailyDto,
)