package com.techmania.weatherproject.usecases

import com.techmania.weatherproject.data.Repository.WeatherRepository
import com.techmania.weatherproject.data.sharedDataComponents.IWeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfoList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FetchWeatherInfoUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): WeatherInfoList {
        val weatherData = weatherRepository.getWeatherData(latitude, longitude)
            val hourly = weatherData.hourly
            val current = weatherData.current
            val daily = weatherData.daily

            val weatherInfoList = WeatherInfoList(
                hourly = parseWeatherInfo(hourly),
                daily = parseWeatherInfo(daily),
                current = parseWeatherInfoSingle(current)
            )
            return weatherInfoList
    }

    private fun parseWeatherInfo(infoList: List<IWeatherInfo>): List<WeatherInfo> {
        val result = mutableListOf<WeatherInfo>()
        infoList.map { info ->
                result.add(
                    WeatherInfo(
                        time = DateTimeFormatter.ISO_DATE_TIME.parse(
                            info.time,
                            LocalDateTime::from
                        ),
                        temperature = info.temperature_2m,
                        apparentTemperature = info.apparent_temperature,
                        precipitation = info.precipitation,
                        weatherDesc = WeatherType.fromWMO(info.weather_code).weatherDesc,
                        iconRes = WeatherType.fromWMO(info.weather_code).iconRes,
                        windSpeed = info.wind_speed_10m
                    )
                )
            }
        return result
    }

    private fun parseWeatherInfoSingle(info: IWeatherInfo): WeatherInfo {
        return WeatherInfo(
            time = DateTimeFormatter.ISO_DATE_TIME.parse(info.time, LocalDateTime::from),
            temperature = info.temperature_2m,
            apparentTemperature = info.apparent_temperature,
            precipitation = info.precipitation,
            weatherDesc = WeatherType.fromWMO(info.weather_code).weatherDesc,
            iconRes = WeatherType.fromWMO(info.weather_code).iconRes,
            windSpeed = info.wind_speed_10m
        )
    }
}