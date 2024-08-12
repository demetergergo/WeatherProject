package com.techmania.weatherproject.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.techmania.weatherproject.R
import com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents.TemperatureIconTimeCardSmall


@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
        ) { innerpadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                TemperatureIconTimeCardSmall(
                    temperature = 19.1,
                    icon = painterResource(id = (R.drawable.sunny)),
                    time = "08:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 19.1,
                    icon = painterResource(id = (R.drawable.cloudy)),
                    time = "10:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 19.1,
                    icon = painterResource(id = (R.drawable.cloudy)),
                    time = "12:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 30.1,
                    icon = painterResource(id = (R.drawable.sunny)),
                    time = "14:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 10.0,
                    icon = painterResource(id = (R.drawable.low_temperature)),
                    time = "16:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = -10.1,
                    icon = painterResource(id = (R.drawable.snow)),
                    time = "18:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 40.1,
                    icon = painterResource(id = (R.drawable.rainy)),
                    time = "20:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 5.6,
                    icon = painterResource(id = (R.drawable.thunderstorm)),
                    time = "22:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
                )
                TemperatureIconTimeCardSmall(
                    temperature = 8.0,
                    icon = painterResource(id = (R.drawable.heavy_rainy)),
                    time = "24:00",
                    onClickCard = { /*TODO*/ },
                    modifier = Modifier.padding(innerpadding)
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