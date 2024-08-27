package com.techmania.weatherproject.presentation.mainScreen

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    val weatherInfoListToDisplay = MutableStateFlow<List<WeatherInfo>>(emptyList())

    var smallCardState by mutableStateOf(LazyListState())
        private set
    var selectedWeatherInfoState by mutableStateOf(null as WeatherInfo?)
        private set
    var selectedBarState by mutableIntStateOf(0)
        private set

    suspend fun fetchWeatherInfo() {
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

                weatherInfoToday.value = WeatherInfoLogic.observeWeatherInfoByDay(
                    weatherData.hourly, LocalDateTime.now()
                )
                weatherInfoTomorrow.value = WeatherInfoLogic.observeWeatherInfoByDay(
                    weatherData.hourly, LocalDateTime.now().plusDays(1)
                )
                weatherInfoCurrent.value = weatherData.current
                weatherInfoDaily.value = weatherData.daily
                selectedWeatherInfoState = weatherData.current
                updateWeatherInfoListToDisplay()
            }
        }
    }

    fun updateSelectedWeatherInfo(weatherInfo: WeatherInfo) {
        selectedWeatherInfoState = weatherInfo
    }

    fun updateSelectedBarState(state: Int) {
        selectedBarState = state
        updateWeatherInfoListToDisplay()
    }

    private fun updateWeatherInfoListToDisplay(){
        weatherInfoListToDisplay.value = when(selectedBarState){
            0 -> weatherInfoToday.value
            1 -> weatherInfoTomorrow.value
            else -> emptyList()
        }
    }
}