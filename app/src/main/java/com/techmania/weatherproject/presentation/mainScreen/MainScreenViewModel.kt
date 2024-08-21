package com.techmania.weatherproject.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.usecases.FetchWeatherInfoUseCase
import com.techmania.weatherproject.usecases.ObserveLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchWeatherInfoUseCase: FetchWeatherInfoUseCase,
    private val observeLocationInfoUseCase: ObserveLocationInfoUseCase
): ViewModel() {

    val weatherInfoAll = MutableStateFlow<List<WeatherInfo>>(emptyList())

    val weatherInfoThisHour = MutableStateFlow<WeatherInfo?>(null)

    val weatherInfoToday = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherInfoTomorrow = MutableStateFlow<List<WeatherInfo>>(emptyList())

    suspend fun fetchWeatherInfo(){
        viewModelScope.launch {
            val locationInfo = observeLocationInfoUseCase()
            Log.d("locationInfo", locationInfo!!.latitude.toString())
            Log.d("locationInfo", locationInfo!!.longitude.toString())

            weatherInfoAll.value = fetchWeatherInfoUseCase(locationInfo!!.latitude, locationInfo!!.longitude)
            weatherInfoToday.value = observeWeatherInfoByDay(LocalDateTime.now())
            weatherInfoTomorrow.value = observeWeatherInfoByDay(LocalDateTime.now().plusDays(1))
            weatherInfoThisHour.value = WeatherInfoLogic.observeWeatherInfoByHour(weatherInfoAll.value ,LocalDateTime.now())
            Log.d("weatherInfoThisHour", weatherInfoThisHour.value.toString())

        }
    }

    private fun observeWeatherInfoByDay(intendedDate: LocalDateTime): List<WeatherInfo> {
        return WeatherInfoLogic.observeWeatherInfoByDay(weatherInfoAll.value, intendedDate)
    }
}