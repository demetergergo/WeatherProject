package com.techmania.weatherproject.presentation.mainScreen

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic.getWeatherInfoIndexFromList
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfoList
import com.techmania.weatherproject.usecases.FetchWeatherInfoUseCase
import com.techmania.weatherproject.usecases.ObserveLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
    private val weatherInfoTomorrow = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherInfoDaily = MutableStateFlow<List<WeatherInfo>>(emptyList())

    var selectedBarState = MutableStateFlow<Int>(0)
    var smallCardState = MutableStateFlow<LazyListState>(LazyListState())

    var selectedWeatherInfoState by mutableStateOf(null as WeatherInfo?)
        private set
    var toggleChipState by mutableStateOf(true)
        private set

    var weatherInfoListToDisplay = combine(
        selectedBarState, weatherInfoToday, weatherInfoTomorrow
    ) { selectedBarState, weatherInfoToday, weatherInfoTomorrow ->
        when (selectedBarState) {
            0 -> weatherInfoToday
            1 -> weatherInfoTomorrow
            else -> weatherInfoToday
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
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
            }
        }
    }

    fun updateSelectedWeatherInfo(weatherInfo: WeatherInfo) {
        selectedWeatherInfoState = weatherInfo
        updateToggleChipState(weatherInfoCurrent.value == selectedWeatherInfoState)
    }

    fun resetSelectedWeatherInfo() {
        selectedWeatherInfoState = weatherInfoCurrent.value
        selectedBarState.value = 0
        updateToggleChipState(weatherInfoCurrent.value == selectedWeatherInfoState)
    }

    fun updateSelectedBarState(state: Int) {
        selectedBarState.value = state
    }

    suspend fun scrollToSelectedWeatherInfo(){
         if (selectedWeatherInfoState != null){
             smallCardState.value.scrollToItem(
                getWeatherInfoIndexFromList(
                    weatherInfoListToDisplay.value, selectedWeatherInfoState!!.time
                )
            )
        }
    }

    private fun updateToggleChipState(active: Boolean){
        toggleChipState = active
    }
}