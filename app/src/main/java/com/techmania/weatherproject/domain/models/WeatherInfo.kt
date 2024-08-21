package com.techmania.weatherproject.domain.models

import java.time.LocalDateTime

data class WeatherInfo(
    val time: LocalDateTime,
    val temperature: Double,
    val apparentTemperature: Double,
    val precipitation: Double,
    val weatherDesc: Int,
    val iconRes: Int,
    val windSpeed: Double,
)