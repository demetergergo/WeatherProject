package com.techmania.weatherproject.domain.logic

import com.techmania.weatherproject.domain.models.WeatherInfo
import java.time.LocalDateTime

object WeatherInfoLogic {
    fun ObserveWeatherInfoByDay(
        weatherInfoList: List<WeatherInfo>,
        intendedDate: LocalDateTime,
    ): List<WeatherInfo> {
        return weatherInfoList.filter { weatherInfo ->
            weatherInfo.time.dayOfMonth == intendedDate.dayOfMonth
        }
    }
}