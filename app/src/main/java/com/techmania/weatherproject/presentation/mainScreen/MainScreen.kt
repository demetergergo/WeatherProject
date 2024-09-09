package com.techmania.weatherproject.presentation.mainScreen

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.BigCurrentInfo
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.ClimateInfoCard
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.SmallCardByDayRow
import kotlinx.coroutines.launch

//for testing
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    onNextSevenDaysClicked: () -> Unit,
) {
    val coarseLocationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    val fineLocationPermissionState =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    if (!fineLocationPermissionState.status.isGranted) {
        LaunchedEffect(fineLocationPermissionState.status) {
            fineLocationPermissionState.launchPermissionRequest()
        }
    }
    LaunchedEffect(
        coarseLocationPermissionState.status.isGranted, fineLocationPermissionState.status.isGranted
    ) {
        if (coarseLocationPermissionState.status.isGranted || fineLocationPermissionState.status.isGranted) {
            mainScreenViewModel.fetchWeatherAndScroll()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()

        val selectedBarState = mainScreenViewModel.selectedBarState.collectAsState()
        val weatherInfoListToDisplay = mainScreenViewModel.weatherInfoListToDisplay.collectAsState()
        val selectedWeatherInfoState = mainScreenViewModel.selectedWeatherInfoState.collectAsState()
        val chipState = mainScreenViewModel.selectedIsCurrentState.collectAsState()
        val smallCardState = mainScreenViewModel.smallCardState.collectAsState()

        val currentLocation = mainScreenViewModel.currentLocation.collectAsState()
        val SelectedLocationName = mainScreenViewModel.selectedLocationName.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(currentLocation.value) {
            if (currentLocation.value != null) {
                mainScreenViewModel.selectLocationNameFromCoordinates(context = context)
            }
        }
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {}, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Search"
                    )
                }
            }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = "Menu"
                    )
                }
            })
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                BigCurrentInfo(
                    weatherInfoCurrent = selectedWeatherInfoState.value
                        ?: WeatherInfoLogic.LoadingWeatherInfo,
                    //TODO: get location name
                    city = SelectedLocationName.value.city,
                    country = SelectedLocationName.value.country,
                    chipText = R.string.current_time,
                    toggleChipOnClick = {
                        mainScreenViewModel.resetSelectedWeatherInfo()
                        coroutineScope.launch {
                            mainScreenViewModel.scrollToSelectedWeatherInfo()
                        }
                    },
                    toggleChipOnSelected = chipState.value,
                    modifier = Modifier.padding(horizontal = 30.dp)
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
                SecondaryTabRow(selectedTabIndex = selectedBarState.value, modifier = Modifier) {
                    Tab(selected = selectedBarState.value == 0,
                        onClick = { mainScreenViewModel.updateSelectedBarState(0) },
                        text = { Text(text = stringResource(id = R.string.today)) })
                    Tab(selected = selectedBarState.value == 1,
                        onClick = { mainScreenViewModel.updateSelectedBarState(1) },
                        text = { Text(text = stringResource(id = R.string.tomorrow)) })

                    TextButton(
                        onClick = onNextSevenDaysClicked,
                        enabled = weatherInfoListToDisplay.value.isNotEmpty()
                    ) {
                        Text(text = stringResource(id = R.string.next7days))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )
                    }
                }
                SmallCardByDayRow(
                    weatherInfoList = weatherInfoListToDisplay.value,
                    onClickCard = { weatherInfo ->
                        mainScreenViewModel.updateSelectedWeatherInfo(weatherInfo)
                    },
                    state = smallCardState.value,
                    selectedCard = selectedWeatherInfoState.value
                        ?: WeatherInfoLogic.LoadingWeatherInfo
                )
            }
        }
    }
}