package com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.techmania.weatherproject.domain.models.WeatherInfo
import java.time.format.DateTimeFormatter

@Composable
fun SmallCardByDayRow(
    weatherInfoList: List<WeatherInfo>,
    onClickCard: () -> Unit,
    padding: PaddingValues
){
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        ) {
        items(weatherInfoList.size){ index  ->
            val weatherInfoSpecific = weatherInfoList[index]
            TemperatureIconTimeCardSmall(
                temperature = weatherInfoSpecific.temperature,
                icon = painterResource(weatherInfoSpecific.iconRes),
                time = DateTimeFormatter.ofPattern("HH:mm").format(weatherInfoSpecific.time),
                onClickCard = onClickCard
            )
        }
    }
}