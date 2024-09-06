package com.techmania.weatherproject.data.sharedDataComponents

interface IWeatherInfo {
    val time: String
    val temperature_2m: Double
    val apparent_temperature: Double
    val precipitation: Double
    val weather_code: Int
    val wind_speed_10m: Double
}