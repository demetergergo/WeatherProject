package com.techmania.weatherproject.presentation.forecastOverviewScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfoList
import com.techmania.weatherproject.usecases.FetchWeatherInfoUseCase
import com.techmania.weatherproject.usecases.ObserveLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastOverviewScreenViewModel @Inject constructor(
    private val fetchWeatherInfoUseCase: FetchWeatherInfoUseCase,
    private val observeLocationInfoUseCase: ObserveLocationInfoUseCase,
) : ViewModel() {
    private val weatherInfoAll = MutableStateFlow<WeatherInfoList?>(null)
    val weatherInfoDaily = MutableStateFlow<List<WeatherInfo>>(emptyList())
    var cardStates = MutableStateFlow<List<Boolean>>(mutableListOf())
        private set

    init {
        viewModelScope.launch {
            fetchWeatherInfo()
        }
    }

    private suspend fun fetchWeatherInfo() {
        viewModelScope.launch {
            try {
                val locationInfo = observeLocationInfoUseCase()
                weatherInfoAll.value =
                    fetchWeatherInfoUseCase(locationInfo!!.latitude, locationInfo.longitude)
            } catch (e: Exception) {
                Log.d("weatherException", e.message.toString())
            }
        }.invokeOnCompletion {
            weatherInfoAll.value?.let { weatherData ->
                weatherInfoDaily.value = weatherData.daily
                cardStates.value = List(weatherInfoDaily.value.size) { false }
            }
        }
    }

    fun updateCardState(index: Int) {
        cardStates.update { list ->
            list.toMutableList().also {
                it[index] = !it[index]
            }
        }
    }
}