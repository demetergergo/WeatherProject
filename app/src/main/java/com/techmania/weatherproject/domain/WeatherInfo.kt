package com.techmania.weatherproject.domain

data class WeatherInfo (
    private val time: String,
    val temperature: Double,
    val apparentTemperature: Double,
    val precipitation: Double,
    val weatherCode: Int,
    val windSpeed: Double,
)