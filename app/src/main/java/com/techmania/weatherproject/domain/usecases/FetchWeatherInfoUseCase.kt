package com.techmania.weatherproject.domain.usecases

import com.techmania.weatherproject.data.networking.OpenMeteoApi
import com.techmania.weatherproject.domain.WeatherInfo
import com.techmania.weatherproject.domain.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FetchWeatherInfoUseCase @Inject constructor(
    private val openMeteoApi: OpenMeteoApi
){
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double): List<WeatherInfo> {
        val weatherData = openMeteoApi.getWeatherData(latitude, longitude)
        if (weatherData?.hourly != null){
            val hourly = weatherData.hourly

            val weatherInfoList = mutableListOf<WeatherInfo>()
            for (i in weatherData.hourly.time.indices) {
                weatherInfoList.add(
                    WeatherInfo(
                        time = DateTimeFormatter.ISO_DATE_TIME.parse(hourly.time[i], LocalDateTime::from),
                        temperature = hourly.temperature_2m[i],
                        apparentTemperature = hourly.apparent_temperature[i],
                        precipitation = hourly.precipitation[i],
                        weatherDesc = WeatherType.fromWMO(hourly.weather_code[i]).weatherDesc,
                        iconRes= WeatherType.fromWMO(hourly.weather_code[i]).iconRes,
                        windSpeed = hourly.wind_speed_10m[i]
                    )
                )
            }
            return weatherInfoList
        }else{
            return emptyList<WeatherInfo>()
        }
    }
}