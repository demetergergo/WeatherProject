package com.techmania.weatherproject.domain.logic

import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.models.LocationNameInfo
import com.techmania.weatherproject.domain.models.WeatherInfo
import java.time.LocalDateTime

object WeatherInfoLogic {
    val LoadingWeatherInfo = WeatherInfo(LocalDateTime.now(), 0.0, 0.0, 0.0, R.string.loading, R.drawable.loading, 0.0)
    val loadingLocationNameInfo = LocationNameInfo("...", "...")

    fun observeWeatherInfoByDay(
        weatherInfoSpecificList: List<WeatherInfo>,
        intendedDate: LocalDateTime,
    ): List<WeatherInfo> {
        return weatherInfoSpecificList.filter { weatherInfo ->
            weatherInfo.time.dayOfMonth == intendedDate.dayOfMonth
        }
    }

    fun getWeatherInfoIndexFromList(list : List<WeatherInfo>, intendedDate: LocalDateTime): Int{
        return list.indexOfFirst { weatherInfo ->
            weatherInfo.time.hour == intendedDate.hour
        }
    }

}
