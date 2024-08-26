package com.techmania.weatherproject.presentation.mainScreen

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfoList
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
    private val observeLocationInfoUseCase: ObserveLocationInfoUseCase,
) : ViewModel() {

    val weatherInfoAll = MutableStateFlow<WeatherInfoList?>(null)
    val weatherInfoCurrent = MutableStateFlow<WeatherInfo?>(null)
    val weatherInfoToday = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherInfoTomorrow = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherInfoDaily = MutableStateFlow<List<WeatherInfo>>(emptyList())

    val smallCardState = MutableStateFlow(LazyListState())
    val selectedWeatherInfoState = MutableStateFlow<WeatherInfo?>(null)

    suspend fun fetchWeatherInfo() {
        viewModelScope.launch {
            try{
                val locationInfo = observeLocationInfoUseCase()
                weatherInfoAll.value = fetchWeatherInfoUseCase(locationInfo!!.latitude, locationInfo.longitude)
            } catch (e: Exception) {
                Log.d("weatherException", e.message.toString())
            }
        }.invokeOnCompletion {
            weatherInfoAll.value?.let{ weatherData ->

                weatherInfoToday.value = WeatherInfoLogic.observeWeatherInfoByDay(weatherData.hourly, LocalDateTime.now())
                weatherInfoTomorrow.value = WeatherInfoLogic.observeWeatherInfoByDay(weatherData.hourly, LocalDateTime.now().plusDays(1))
                weatherInfoCurrent.value = weatherData.current
                weatherInfoDaily.value = weatherData.daily
                selectedWeatherInfoState.value = weatherData.current
            }
        }
    }

    fun updateSelectedWeatherInfo(weatherInfo: WeatherInfo){
        selectedWeatherInfoState.value = weatherInfo
    }
}