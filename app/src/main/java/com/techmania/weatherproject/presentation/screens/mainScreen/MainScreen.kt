package com.techmania.weatherproject.presentation.screens.mainScreen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.logic.WeatherInfoLogic
import com.techmania.weatherproject.presentation.permissions.LocationPermissions
import com.techmania.weatherproject.presentation.screens.mainScreen.mainScreenComponents.BigCurrentInfo
import com.techmania.weatherproject.presentation.screens.mainScreen.mainScreenComponents.ClimateInfoCard
import com.techmania.weatherproject.presentation.screens.mainScreen.mainScreenComponents.SmallCardByDayRow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
    onNextSevenDaysClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    LocationPermissions()
    LaunchedEffect(
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION).status.isGranted
    ) {
        mainScreenViewModel.fetchWeatherInfo()
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

        val refreshState by mainScreenViewModel.refreshState.collectAsStateWithLifecycle()
        val pullToRefreshState = rememberPullToRefreshState()

        val currentLocation = mainScreenViewModel.currentLocation.collectAsState()
        val selectedLocationName = mainScreenViewModel.selectedLocationName.collectAsState()
        val context = LocalContext.current

        LaunchedEffect(currentLocation.value) {
            if (currentLocation.value != null) {
                mainScreenViewModel.selectLocationNameFromCoordinates(context = context)
            }
        }
        LaunchedEffect(weatherInfoListToDisplay.value) {
            mainScreenViewModel.scrollToSelectedWeatherInfo()
        }
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {}, actions = {
                IconButton(onClick = {
                    onSettingsClicked()
                    coroutineScope.launch {
                        delay(500)
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = "Menu"
                    )
                }
            })
        }) { innerPadding ->
            PullToRefreshBox(
                modifier = Modifier.padding(innerPadding),
                state = pullToRefreshState,
                isRefreshing = refreshState,
                onRefresh = {
                    mainScreenViewModel.onPullRefresh()
                },
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        BigCurrentInfo(
                            weatherInfoCurrent = selectedWeatherInfoState.value
                                ?: WeatherInfoLogic.LoadingWeatherInfo,
                            city = selectedLocationName.value.city,
                            country = selectedLocationName.value.country,
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
                                R.string.unit_mm
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
                        SecondaryTabRow(
                            selectedTabIndex = selectedBarState.value, modifier = Modifier
                        ) {
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
    }
}