package com.techmania.weatherproject.data.networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfoListDto(
    @Json(name = "hourly") val hourly: WeatherInfoDto,
)
