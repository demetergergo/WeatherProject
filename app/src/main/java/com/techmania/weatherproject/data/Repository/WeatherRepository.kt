package com.techmania.weatherproject.data.Repository

import com.techmania.weatherproject.data.database.dao.WeatherInfoCurrentDao
import com.techmania.weatherproject.data.database.dao.WeatherInfoDailyDao
import com.techmania.weatherproject.data.database.dao.WeatherInfoHourlyDao
import com.techmania.weatherproject.data.database.table.WeatherInfo
import com.techmania.weatherproject.data.database.table.WeatherInfoCurrent
import com.techmania.weatherproject.data.database.table.WeatherInfoDaily
import com.techmania.weatherproject.data.networking.OpenMeteoApi
import com.techmania.weatherproject.data.networking.dto.IWeatherInfoDto
import com.techmania.weatherproject.data.networking.dto.WeatherInfoCurrentDto
import com.techmania.weatherproject.data.networking.dto.WeatherInfoDto
import com.techmania.weatherproject.data.networking.dto.WeatherInfoListDto
import com.techmania.weatherproject.data.sharedDataComponents.IWeatherInfo
import com.techmania.weatherproject.data.sharedDataComponents.IWeatherInfoList
import java.time.LocalDateTime
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val openMeteoApi: OpenMeteoApi,
    private val weatherInfoCurrentDao: WeatherInfoCurrentDao,
    private val weatherInfoHourlyDao: WeatherInfoHourlyDao,
    private val weatherInfoDailyDao: WeatherInfoDailyDao,
) {
    @Suppress("UNCHECKED_CAST")
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
    ): IWeatherInfoList {
        val current: WeatherInfoCurrent? = weatherInfoCurrentDao.getAll()
        if (current != null && current.time > LocalDateTime.now().minusMinutes(15).toString()) {
            return fromDatabase()
        } else {
            val weatherData = openMeteoApi.getWeatherData(latitude, longitude)
            val parsedWeatherData = weatherData.toIWeatherInfoList()

            weatherInfoCurrentDao.nukeAll()
            weatherInfoHourlyDao.nukeAll()
            weatherInfoDailyDao.nukeAll()

            weatherInfoCurrentDao.insertAll(parsedWeatherData.current as WeatherInfoCurrent)
            weatherInfoHourlyDao.insertAll(parsedWeatherData.hourly as List<WeatherInfo>)
            weatherInfoDailyDao.insertAll(parsedWeatherData.daily as List<WeatherInfoDaily>)
            return fromDatabase()
        }
    }

    private fun fromDatabase(): IWeatherInfoList {
        return object : IWeatherInfoList {
            override val current: WeatherInfoCurrent = weatherInfoCurrentDao.getAll()!!
            override val daily: List<IWeatherInfo> = weatherInfoDailyDao.getAll()
            override val hourly: List<IWeatherInfo> = weatherInfoHourlyDao.getAll()
        }
    }

    private fun WeatherInfoListDto.toIWeatherInfoList(): IWeatherInfoList {
        val hourlyData = parseWeatherInfo(this.hourly)
        val currentData = parseWeatherInfoSingle(this.current)
        val dailyData = parseWeatherInfo(this.daily)

        return object : IWeatherInfoList {
            override val hourly: List<IWeatherInfo> = hourlyData
            override val current: IWeatherInfo = currentData
            override val daily: List<IWeatherInfo> = dailyData
        }
    }

    private fun parseWeatherInfo(dto: IWeatherInfoDto): List<IWeatherInfo> {
        return dto.time.indices.map { i ->
            if (dto is WeatherInfoDto) {
                WeatherInfo(
                    id = 0,
                    time = dto.time[i],
                    temperature_2m = dto.temperature_2m[i],
                    apparent_temperature = dto.apparent_temperature[i],
                    precipitation = dto.precipitation[i],
                    weather_code = dto.weather_code[i],
                    wind_speed_10m = dto.wind_speed_10m[i]
                )
            } else {
                WeatherInfoDaily(
                    id = 0,
                    time = "${dto.time[i]}T00:00",
                    temperature_2m = dto.temperature_2m[i],
                    apparent_temperature = dto.apparent_temperature[i],
                    precipitation = dto.precipitation[i],
                    weather_code = dto.weather_code[i],
                    wind_speed_10m = dto.wind_speed_10m[i]
                )
            }
        }
    }

    private fun parseWeatherInfoSingle(dto: WeatherInfoCurrentDto): WeatherInfoCurrent {
        return WeatherInfoCurrent(
            id = 0,
            time = dto.time,
            temperature_2m = dto.temperature_2m,
            apparent_temperature = dto.apparent_temperature,
            precipitation = dto.precipitation,
            weather_code = dto.weather_code,
            wind_speed_10m = dto.wind_speed_10m
        )
    }
}
