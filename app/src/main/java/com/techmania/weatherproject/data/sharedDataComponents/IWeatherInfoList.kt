package com.techmania.weatherproject.data.sharedDataComponents

interface IWeatherInfoList {
    val hourly: List<IWeatherInfo>
    val current: IWeatherInfo
    val daily: List<IWeatherInfo>
}