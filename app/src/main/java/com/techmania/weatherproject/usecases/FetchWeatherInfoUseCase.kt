package com.techmania.weatherproject.usecases

import com.techmania.weatherproject.data.networking.Dto.IWeatherInfoDto
import com.techmania.weatherproject.data.networking.Dto.WeatherInfoCurrentDto
import com.techmania.weatherproject.data.networking.Dto.WeatherInfoDto
import com.techmania.weatherproject.data.networking.OpenMeteoApi
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfoList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FetchWeatherInfoUseCase @Inject constructor(
    private val openMeteoApi: OpenMeteoApi,
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
    ): WeatherInfoList {
        val weatherData = openMeteoApi.getWeatherData(latitude, longitude)
        if (weatherData?.hourly != null) {
            val hourly = weatherData.hourly
            val current = weatherData.current
            val daily = weatherData.daily

            val weatherInfoList = WeatherInfoList(
                hourly = parseWeatherInfo(hourly),
                daily = parseWeatherInfo(daily),
                current = parseWeatherInfoSingle(current)
            )
            return weatherInfoList
        } else {
            throw Exception("Weather data is null")
        }
    }

    private fun parseWeatherInfo(dto: IWeatherInfoDto): List<WeatherInfo> {
        val result = mutableListOf<WeatherInfo>()
            for (i in dto.time.indices) {
                result.add(
                    WeatherInfo(
                        time = if(dto is WeatherInfoDto){
                            DateTimeFormatter.ISO_DATE_TIME.parse(dto.time[i], LocalDateTime::from)
                        } else{
                            //ugly, but works
                            DateTimeFormatter.ISO_DATE_TIME.parse("${dto.time[i]}T00:00", LocalDateTime::from)
                        },
                        temperature = dto.temperature_2m[i],
                        apparentTemperature = dto.apparent_temperature[i],
                        precipitation = dto.precipitation[i],
                        weatherDesc = WeatherType.fromWMO(dto.weather_code[i]).weatherDesc,
                        iconRes = WeatherType.fromWMO(dto.weather_code[i]).iconRes,
                        windSpeed = dto.wind_speed_10m[i]
                    )
                )
            }
        return result
    }

    private fun parseWeatherInfoSingle(dto: WeatherInfoCurrentDto): WeatherInfo {
        return WeatherInfo(
            time = DateTimeFormatter.ISO_DATE_TIME.parse(dto.time, LocalDateTime::from),
            temperature = dto.temperature_2m,
            apparentTemperature = dto.apparent_temperature,
            precipitation = dto.precipitation,
            weatherDesc = WeatherType.fromWMO(dto.weather_code).weatherDesc,
            iconRes = WeatherType.fromWMO(dto.weather_code).iconRes,
            windSpeed = dto.wind_speed_10m
        )
    }
}