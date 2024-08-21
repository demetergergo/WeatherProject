package com.techmania.weatherproject.domain.logic

import com.techmania.weatherproject.domain.models.WeatherInfo
import java.time.LocalDateTime

object WeatherInfoLogic {
    fun observeWeatherInfoByDay(
        weatherInfoList: List<WeatherInfo>,
        intendedDate: LocalDateTime,
    ): List<WeatherInfo> {
        return weatherInfoList.filter { weatherInfo ->
            weatherInfo.time.dayOfMonth == intendedDate.dayOfMonth
        }
    }

    fun observeWeatherInfoByHour(
        weatherInfoList: List<WeatherInfo>,
        intendedDate: LocalDateTime,
    ): WeatherInfo{
        return weatherInfoList.find { weatherInfo ->
            weatherInfo.time.dayOfMonth == intendedDate.dayOfMonth && weatherInfo.time.hour == intendedDate.hour
        }!!
    }
}