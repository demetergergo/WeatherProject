package com.techmania.weatherproject.domain.logic

import com.techmania.weatherproject.domain.models.WeatherInfo
import java.time.LocalDateTime

object WeatherInfoLogic {
    fun observeWeatherInfoByDay(
        weatherInfoSpecificList: List<WeatherInfo>,
        intendedDate: LocalDateTime,
    ): List<WeatherInfo> {
        return weatherInfoSpecificList.filter { weatherInfo ->
            weatherInfo.time.dayOfMonth == intendedDate.dayOfMonth
        }
    }
}
