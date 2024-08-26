package com.techmania.weatherproject.presentation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic.getWeatherInfoIndexFromList
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.BigCurrentInfo
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.ClimateInfoCard
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.SmallCardByDayRow

//for testing
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val weatherInfoLatest = mainScreenViewModel.weatherInfoAll.collectAsState()
        val weatherInfoToday = mainScreenViewModel.weatherInfoToday.collectAsState()
        val weatherInfoTomorrow = mainScreenViewModel.weatherInfoTomorrow.collectAsState()
        val weatherInfoCurrent = mainScreenViewModel.weatherInfoCurrent.collectAsState()
        val smallCardState = mainScreenViewModel.smallCardState.collectAsState()
        val selectedWeatherInfoState = mainScreenViewModel.selectedWeatherInfoState.collectAsState()



        LaunchedEffect(Unit) {
            mainScreenViewModel.fetchWeatherInfo()
        }
        LaunchedEffect(weatherInfoToday.value) {
            if (selectedWeatherInfoState.value != null) {
                smallCardState.value.scrollToItem(
                    getWeatherInfoIndexFromList(
                        weatherInfoToday.value, selectedWeatherInfoState.value!!.time
                    )
                )
            }
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                BigCurrentInfo(
                    weatherInfoCurrent = selectedWeatherInfoState.value
                        ?: WeatherInfoLogic.LoadingWeatherInfo,
                    city = "Budapest",
                    country = "Hungary",
                    modifier = Modifier.padding(20.dp)
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(15.dp)
                ) {
                    ClimateInfoCard(
                        R.drawable.rainy,
                        R.string.rainfall,
                        selectedWeatherInfoState.value?.precipitation
                            ?: WeatherInfoLogic.LoadingWeatherInfo.precipitation,
                        R.string.unit_cm
                    )
                    ClimateInfoCard(
                        R.drawable.wind_direction,
                        R.string.wind,
                        selectedWeatherInfoState.value?.windSpeed
                            ?: WeatherInfoLogic.LoadingWeatherInfo.windSpeed,
                        R.string.unit_kmh
                    )
                    ClimateInfoCard(
                        R.drawable.sunset,
                        R.string.apparent_temperature,
                        selectedWeatherInfoState.value?.apparentTemperature
                            ?: WeatherInfoLogic.LoadingWeatherInfo.apparentTemperature,
                        R.string.unit_celsius
                    )
                }
                SmallCardByDayRow(
                    weatherInfoList = weatherInfoToday.value,
                    onClickCard = { weatherInfo ->
                        mainScreenViewModel.updateSelectedWeatherInfo(weatherInfo)
                    },
                    padding = innerPadding,
                    state = smallCardState.value
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}