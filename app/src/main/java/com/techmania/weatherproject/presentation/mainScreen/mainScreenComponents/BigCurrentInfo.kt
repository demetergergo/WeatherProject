package com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.presentation.sharedComponents.ImageWithShadow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BigCurrentInfo(
    weatherInfoCurrent: WeatherInfo,
    city: String,
    country: String,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "$city,", fontSize = 35.sp)
        Text(text = country, fontSize = 35.sp)
        Text(text = weatherInfoCurrent.time.format(DateTimeFormatter.ofPattern("EEE, MMM dd")))
        Row(modifier = Modifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            ImageWithShadow(weatherInfoCurrent.iconRes, weatherInfoCurrent.weatherDesc, padding = 5.dp, shadowColor = Color.DarkGray, modifier = Modifier.size(150.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(){
                    Text(text = weatherInfoCurrent.temperature.toString(), fontSize = 60.sp)
                    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start){
                        Text(text = stringResource(id = R.string.unit_celsius), fontSize = 20.sp)
                    }
                }
                Text(text = stringResource(id = weatherInfoCurrent.weatherDesc), fontSize= 25.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BigCurrentInfoPreview() {
    BigCurrentInfo(
        WeatherInfo(LocalDateTime.now(), 25.0, 26.0, 1.0, 1, 1, 1.1),
        "Budapest",
        "Hungary",
        Modifier
    )
}