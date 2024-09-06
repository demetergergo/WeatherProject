package com.techmania.weatherproject.data.networking.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class WeatherInfoDto (
    @Json(name = "time") override val time: List<String>,
    @Json(name = "temperature_2m") override val temperature_2m: List<Double>,
    @Json(name = "apparent_temperature") override val apparent_temperature: List<Double>,
    @Json(name = "precipitation") override val precipitation: List<Double>,
    @Json(name = "weather_code") override val weather_code: List<Int>,
    @Json(name = "wind_speed_10m") override val wind_speed_10m: List<Double>,
) : IWeatherInfoDto
