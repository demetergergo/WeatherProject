package com.techmania.weatherproject.presentation.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmania.weatherproject.domain.WeatherInfo
import com.techmania.weatherproject.domain.usecases.FetchWeatherInfoUseCase
import com.techmania.weatherproject.domain.usecases.ObserveWeatherInfoByDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchWeatherInfoUseCase: FetchWeatherInfoUseCase,
    private val observeWeatherInfoByDayUseCase: ObserveWeatherInfoByDayUseCase
): ViewModel() {

    val weatherInfoAll = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherInfoToday = MutableStateFlow<List<WeatherInfo>>(emptyList())
    val weatherInfoTomorrow = MutableStateFlow<List<WeatherInfo>>(emptyList())

    suspend fun fetchWeatherInfo(){
        viewModelScope.launch {
            weatherInfoAll.value = fetchWeatherInfoUseCase(50.0, 50.0)
            weatherInfoToday.value = observeWeatherInfoByDayUseCase(weatherInfoAll.value, LocalDateTime.now())
            weatherInfoTomorrow.value = observeWeatherInfoByDayUseCase(weatherInfoAll.value, LocalDateTime.now().plusDays(1))
        }
    }

    fun observeWeatherInfoByDay(intendedDate: LocalDateTime): List<WeatherInfo> {
        return observeWeatherInfoByDayUseCase(weatherInfoAll.value, intendedDate)
    }
}