package com.techmania.weatherproject.presentation.mainScreen

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic.getWeatherInfoIndexFromList
import com.techmania.weatherproject.domain.models.LocationInfo
import com.techmania.weatherproject.domain.models.LocationNameInfo
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.domain.models.WeatherInfoList
import com.techmania.weatherproject.usecases.FetchLocationNameUseCase
import com.techmania.weatherproject.usecases.FetchWeatherInfoUseCase
import com.techmania.weatherproject.usecases.ObserveLocationInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchWeatherInfoUseCase: FetchWeatherInfoUseCase,
    private val observeLocationInfoUseCase: ObserveLocationInfoUseCase,
    private val fetchLocationNameUseCase: FetchLocationNameUseCase,
) : ViewModel() {

    private val weatherInfoAll = MutableStateFlow<WeatherInfoList?>(null)
    private val weatherInfoCurrent = MutableStateFlow<WeatherInfo?>(null)
    private val weatherInfoToday = MutableStateFlow<List<WeatherInfo>>(emptyList())
    private val weatherInfoTomorrow = MutableStateFlow<List<WeatherInfo>>(emptyList())
    private val weatherInfoDaily = MutableStateFlow<List<WeatherInfo>>(emptyList())

    var currentLocation = MutableStateFlow<LocationInfo?>(null)
        private set
    var selectedLocationName =
        MutableStateFlow<LocationNameInfo>(WeatherInfoLogic.loadingLocationNameInfo)
        private set

    var settingsButtonEnabled = mutableStateOf(true)

    var selectedBarState = MutableStateFlow<Int>(0)
        private set
    var smallCardState = MutableStateFlow<LazyListState>(LazyListState())
        private set
    var selectedWeatherInfoState = MutableStateFlow<WeatherInfo?>(null)
        private set
    var selectedIsCurrentState = MutableStateFlow<Boolean>(true)
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

    init {
        fetchWeatherAndScroll()
    }

    fun fetchWeatherAndScroll() {
        viewModelScope.launch {
            fetchWeatherInfo()
        }.invokeOnCompletion {
            viewModelScope.launch {
                scrollToSelectedWeatherInfo()
            }
        }
    }

    private suspend fun fetchWeatherInfo() {
        viewModelScope.launch {
            try {
                currentLocation.value = observeLocationInfoUseCase()
                withContext(Dispatchers.IO) {
                    weatherInfoAll.value =
                        fetchWeatherInfoUseCase(
                            currentLocation.value!!.latitude,
                            currentLocation.value!!.longitude
                        )
                }
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
                selectedWeatherInfoState.value = weatherData.current
            }
        }
    }

    fun updateSelectedWeatherInfo(weatherInfo: WeatherInfo) {
        selectedWeatherInfoState.value = weatherInfo
        updateToggleChipState(weatherInfoCurrent.value == selectedWeatherInfoState.value)
    }

    fun resetSelectedWeatherInfo() {
        selectedWeatherInfoState.value = weatherInfoCurrent.value
        selectedBarState.value = 0
        updateToggleChipState(weatherInfoCurrent.value == selectedWeatherInfoState.value)
    }

    fun updateSelectedBarState(state: Int) {
        selectedBarState.value = state
    }

    suspend fun scrollToSelectedWeatherInfo() {
        if (selectedWeatherInfoState.value != null) {
            smallCardState.value.scrollToItem(
                getWeatherInfoIndexFromList(
                    weatherInfoListToDisplay.value, selectedWeatherInfoState.value!!.time
                )
            )
        }
    }

    private fun updateToggleChipState(active: Boolean) {
        selectedIsCurrentState.value = active
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun selectLocationNameFromCoordinates(context: Context) {
        try {
            selectedLocationName.value = fetchLocationNameUseCase(
                currentLocation.value!!.latitude,
                currentLocation.value!!.longitude,
                context
            )
        } catch (e: Exception) {
            Log.d("locationException", e.message.toString())
        }
    }
}
