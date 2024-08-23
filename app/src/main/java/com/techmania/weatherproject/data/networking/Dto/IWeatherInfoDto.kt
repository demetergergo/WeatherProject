package com.techmania.weatherproject.data.networking.Dto

interface IWeatherInfoDto {
    val time: List<String>
    val temperature_2m: List<Double>
    val apparent_temperature: List<Double>
    val precipitation: List<Double>
    val weather_code: List<Int>
    val wind_speed_10m: List<Double>
}