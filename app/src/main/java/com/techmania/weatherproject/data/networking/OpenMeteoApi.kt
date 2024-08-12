package com.techmania.weatherproject.data.networking

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/forecast?hourly=temperature_2m,apparent_temperature,precipitation,weather_code,wind_speed_10m")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): WeatherInfoListDto?
}