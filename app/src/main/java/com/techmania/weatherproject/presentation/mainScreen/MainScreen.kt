package com.techmania.weatherproject.presentation.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val weatherInfoToday = mainScreenViewModel.weatherInfoToday.collectAsState()
        val weatherInfoCurrent = mainScreenViewModel.weatherInfoCurrent.collectAsState()
        val weatherInfoListToDisplay = mainScreenViewModel.weatherInfoListToDisplay.collectAsState()


        LaunchedEffect(Unit) {
            mainScreenViewModel.fetchWeatherInfo()
        }
        LaunchedEffect(weatherInfoToday.value) {
            if (mainScreenViewModel.selectedWeatherInfoState != null) {
                mainScreenViewModel.smallCardState.scrollToItem(
                    getWeatherInfoIndexFromList(
                        weatherInfoToday.value, mainScreenViewModel.selectedWeatherInfoState!!.time
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
                    weatherInfoCurrent = mainScreenViewModel.selectedWeatherInfoState
                        ?: WeatherInfoLogic.LoadingWeatherInfo,
                    //TODO: get location name
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
                        mainScreenViewModel.selectedWeatherInfoState?.precipitation
                            ?: WeatherInfoLogic.LoadingWeatherInfo.precipitation,
                        R.string.unit_cm
                    )
                    ClimateInfoCard(
                        R.drawable.wind_direction,
                        R.string.wind,
                        mainScreenViewModel.selectedWeatherInfoState?.windSpeed
                            ?: WeatherInfoLogic.LoadingWeatherInfo.windSpeed,
                        R.string.unit_kmh
                    )
                    ClimateInfoCard(
                        R.drawable.sunset,
                        R.string.apparent_temperature,
                        mainScreenViewModel.selectedWeatherInfoState?.apparentTemperature
                            ?: WeatherInfoLogic.LoadingWeatherInfo.apparentTemperature,
                        R.string.unit_celsius
                    )
                }
                
                SecondaryTabRow(selectedTabIndex = mainScreenViewModel.selectedBarState) {
                    Tab(
                        selected = mainScreenViewModel.selectedBarState == 0,
                        onClick = { mainScreenViewModel.updateSelectedBarState(0) },
                        text = { Text(text = "Today")}
                    )
                    Tab(
                        selected = mainScreenViewModel.selectedBarState == 1,
                        onClick = { mainScreenViewModel.updateSelectedBarState(1) },
                        text = { Text(text = "Tomorrow")}
                    )
                }
                
                SmallCardByDayRow(
                    weatherInfoList = weatherInfoListToDisplay.value,
                    onClickCard = { weatherInfo ->
                        mainScreenViewModel.updateSelectedWeatherInfo(weatherInfo)
                    },
                    padding = innerPadding,
                    state = mainScreenViewModel.smallCardState,
                    selectedCard = mainScreenViewModel.selectedWeatherInfoState ?: WeatherInfoLogic.LoadingWeatherInfo
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