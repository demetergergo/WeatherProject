package com.techmania.weatherproject.domain.usecases

import com.techmania.weatherproject.domain.WeatherInfo
import java.time.LocalDateTime
import javax.inject.Inject

class ObserveWeatherInfoByDayUseCase @Inject constructor(){
    operator fun invoke(
        weatherInfoList : List<WeatherInfo>,
        intendedDate: LocalDateTime): List<WeatherInfo>{
        return weatherInfoList.filter {
            weatherInfo->
            weatherInfo.time.dayOfMonth == intendedDate.dayOfMonth
        }
    }
}