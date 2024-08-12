package com.techmania.weatherproject.data.networking


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class WeatherInfoDto (
    @Json(name = "time") val time: List<String>,
    @Json(name = "temperature_2m") val temperature_2m: List<Double>,
    @Json(name = "apparent_temperature") val apparent_temperature: List<Double>,
    @Json(name = "precipitation") val precipitation: List<Double>,
    @Json(name = "weather_code") val weather_code: List<Int>,
    @Json(name = "wind_speed_10m") val wind_speed_10m: List<Double>,
)
