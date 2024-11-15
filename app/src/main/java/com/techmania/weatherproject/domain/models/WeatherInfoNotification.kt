package com.techmania.weatherproject.domain.models

data class WeatherInfoNotification(
    val temperature: Double,
    val weatherDesc: Int,
    val iconRes: Int,
)