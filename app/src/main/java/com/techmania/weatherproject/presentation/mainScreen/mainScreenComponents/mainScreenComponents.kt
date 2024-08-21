package com.techmania.weatherproject.presentation.mainScreen.mainScreenComponents


import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults
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

@Composable
fun TemperatureIconTimeCardSmall(temperature: Double, icon: Painter, time: String, onClickCard: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .height(110.dp)
            .width(65.dp)
            .padding(5.dp)
        ,shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        onClick = onClickCard
    ) {
        Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment= Alignment.CenterHorizontally){
                Text(time, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                Image(icon, contentDescription = "icon", modifier = Modifier.weight(1.5f))
                Text("$temperature°C", textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TemperatureIconTimeCardSmallPreview() {
    TemperatureIconTimeCardSmall(temperature = -18.0, icon = painterResource(R.drawable.sunny), time = "12:13", {})
}