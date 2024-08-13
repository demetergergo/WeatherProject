package com.techmania.weatherproject.domain

import java.time.LocalDateTime

data class WeatherInfo (
    val time: LocalDateTime,
    val temperature: Double,
    val apparentTemperature: Double,
    val precipitation: Double,
    val weatherDesc: String,
    val iconRes: Int,
    val windSpeed: Double,
)
