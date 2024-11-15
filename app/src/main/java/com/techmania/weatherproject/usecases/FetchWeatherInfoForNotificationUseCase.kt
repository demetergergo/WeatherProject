package com.techmania.weatherproject.usecases

import com.techmania.weatherproject.data.networking.OpenMeteoApi
import com.techmania.weatherproject.domain.models.WeatherInfoNotification
import javax.inject.Inject

class FetchWeatherInfoForNotificationUseCase @Inject constructor(private val OpenMeteoApi: OpenMeteoApi) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): WeatherInfoNotification {
        val weatherData = OpenMeteoApi.getWeatherDataNotification(latitude, longitude)
        val current = weatherData.current

        val weatherInfoNotification = WeatherInfoNotification(
            temperature = current.temperature_2m,
            weatherDesc = WeatherType.fromWMO(current.weather_code).weatherDesc,
            iconRes = WeatherType.fromWMO(current.weather_code).iconRes
        )
        return weatherInfoNotification
    }
}