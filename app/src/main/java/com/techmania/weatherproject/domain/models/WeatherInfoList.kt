package com.techmania.weatherproject.domain.models

data class WeatherInfoList (
    val hourly: List<WeatherInfo>,
    val daily: List<WeatherInfo>,
    val current: WeatherInfo,
)