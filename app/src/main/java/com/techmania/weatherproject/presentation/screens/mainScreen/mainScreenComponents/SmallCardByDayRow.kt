package com.techmania.weatherproject.presentation.screens.mainScreen.mainScreenComponents


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.models.WeatherInfo
import java.time.format.DateTimeFormatter

@Composable
fun SmallCardByDayRow(
    weatherInfoList: List<WeatherInfo>,
    onClickCard: (WeatherInfo) -> Unit,
    state: LazyListState = rememberLazyListState(),
    selectedCard: WeatherInfo?
){
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        state = state
    ) {
        items(weatherInfoList.size){ index  ->
            val weatherInfoSpecific = weatherInfoList[index]
            SmallCard(
                temperature = weatherInfoSpecific.temperature,
                icon = painterResource(weatherInfoSpecific.iconRes),
                time = DateTimeFormatter.ofPattern("HH:mm").format(weatherInfoSpecific.time),
                onClickCard =  {onClickCard(weatherInfoSpecific)},
                cardColor = if (selectedCard?.time?.hour == weatherInfoSpecific.time.hour && selectedCard.time.dayOfMonth == weatherInfoSpecific.time.dayOfMonth) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent
            )
        }
    }
}

@Composable
fun SmallCard(temperature: Double, icon: Painter, time: String, onClickCard: () -> Unit, cardColor : Color, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .height(120.dp)
            .width(65.dp)
            .padding(5.dp)
        ,shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(cardColor),
        onClick = onClickCard
    ) {
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment= Alignment.CenterHorizontally){
            Text(
                time, textAlign = TextAlign.Center, modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            )
                Image(icon, contentDescription = "icon", modifier = Modifier.weight(1.5f))
                Text("$temperature°", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
    }
}


@Preview(showBackground = false)
@Composable
fun TemperatureIconTimeCardSmallPreview() {
    SmallCard(temperature = -18.0, icon = painterResource(R.drawable.sunny), time = "12:13", {}, cardColor = Color.Transparent)
}