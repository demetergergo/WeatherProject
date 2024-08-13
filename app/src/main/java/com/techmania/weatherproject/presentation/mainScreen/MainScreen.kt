package com.techmania.weatherproject.presentation.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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

        LaunchedEffect(Unit) {
            mainScreenViewModel.fetchWeatherInfo()
        }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            SmallCardByDayRow(
                weatherInfoList = weatherInfoToday.value, onClickCard = {}, padding = innerPadding
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}