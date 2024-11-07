package com.techmania.weatherproject.presentation.screens.mainScreen.mainScreenComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.techmania.weatherproject.presentation.sharedComponents.ImageWithShadow

@Composable
fun ClimateInfoCard(
    imageResourceId: Int,
    textResourceId: Int,
    amount: Double,
    unitResourceId: Int,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedCard(modifier = Modifier, colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(10.dp)){
                ImageWithShadow(
                    imageResource = imageResourceId,
                    contentDescription = textResourceId,
                    padding = 2.dp,
                    modifier = modifier.size(45.dp)
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
            Text(text = stringResource(textResourceId), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${amount.toString()} ${stringResource(unitResourceId)}",
                textAlign = TextAlign.Center
            )
        }
    }
}