package com.techmania.weatherproject.presentation.screens.forecastOverviewScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techmania.weatherproject.R
import com.techmania.weatherproject.presentation.sharedComponents.ClimateInfoCardVertical
import com.techmania.weatherproject.presentation.sharedComponents.ExpandableCard
import com.techmania.weatherproject.presentation.sharedComponents.ImageWithShadow
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastOverViewScreen(
    forecastOverViewScreenViewModel: ForecastOverviewScreenViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {

    val weatherInfoDaily = forecastOverViewScreenViewModel.weatherInfoDaily.collectAsState()
    val cardStates = forecastOverViewScreenViewModel.cardStates.collectAsState()

    val refreshState = forecastOverViewScreenViewModel.refreshState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.next7days)) },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
        )
    }) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.padding(innerPadding),
            state = pullToRefreshState,
            isRefreshing = refreshState.value,
            onRefresh = {
                forecastOverViewScreenViewModel.onPullRefresh()
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(weatherInfoDaily.value.size) { index ->
                    val weatherInfoSpecific = weatherInfoDaily.value[index]
                    ExpandableCard(overView = {
                        Text(
                            text = weatherInfoSpecific.time.format(DateTimeFormatter.ofPattern("EEEE"))
                                .replaceFirstChar { it.titlecase(Locale.getDefault()) },
                            modifier = Modifier.weight(3f)
                        )
                        Spacer(modifier = Modifier.weight(2f))
                        Text(
                            text = "${weatherInfoSpecific.apparentTemperature}°",
                            modifier = Modifier.weight(1.5f)
                        )
                        ImageWithShadow(
                            imageResource = weatherInfoSpecific.iconRes,
                            contentDescription = weatherInfoSpecific.weatherDesc,
                            modifier = Modifier.weight(1.5f),
                            padding = 5.dp,
                            shadowColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }, expandedState = cardStates.value[index], onClick = {
                        forecastOverViewScreenViewModel.updateCardState(index)
                    }, details = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ClimateInfoCardVertical(
                                imageResourceId = R.drawable.rainy,
                                textResourceId = R.string.rainfall,
                                amount = weatherInfoDaily.value[index].precipitation,
                                unitResourceId = R.string.unit_mm,
                                modifier = Modifier
                            )
                            ClimateInfoCardVertical(
                                imageResourceId = R.drawable.wind_direction,
                                textResourceId = R.string.wind,
                                amount = weatherInfoDaily.value[index].windSpeed,
                                unitResourceId = R.string.unit_kmh,
                                modifier = Modifier
                            )
                            ClimateInfoCardVertical(
                                imageResourceId = R.drawable.sunset,
                                textResourceId = R.string.apparent_temperature,
                                amount = weatherInfoDaily.value[index].apparentTemperature,
                                unitResourceId = R.string.unit_celsius,
                                modifier = Modifier
                            )
                        }
                    })
                }
            }
        }
    }
}