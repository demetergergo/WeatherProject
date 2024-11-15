package com.techmania.weatherproject.data.networking

import com.techmania.weatherproject.data.networking.dto.WeatherInfoListDto
import com.techmania.weatherproject.data.networking.dto.WeatherInfoNotificationDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi {
    @GET("v1/forecast?current=temperature_2m,apparent_temperature,precipitation,weather_code,wind_speed_10m&hourly=temperature_2m,apparent_temperature,precipitation,weather_code,wind_speed_10m&daily=weather_code,temperature_2m_max,apparent_temperature_max,precipitation_sum,wind_speed_10m_max&timezone=auto")
    suspend fun getWeatherData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): WeatherInfoListDto

    @GET("v1/forecast?current=temperature_2m,weather_code")
    suspend fun getWeatherDataNotification(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): WeatherInfoNotificationDto
}