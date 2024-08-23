package com.techmania.weatherproject.data.networking.Dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//not a list!!
@JsonClass(generateAdapter = true)
data class WeatherInfoDailyDto (
    @Json(name = "time") override val time: List<String>,
    @Json(name = "temperature_2m_max") override val temperature_2m: List<Double>,
    @Json(name = "apparent_temperature_max") override val apparent_temperature: List<Double>,
    @Json(name = "precipitation_sum") override val precipitation: List<Double>,
    @Json(name = "weather_code") override val weather_code: List<Int>,
    @Json(name = "wind_speed_10m_max") override val wind_speed_10m: List<Double>,
) : IWeatherInfoDto
