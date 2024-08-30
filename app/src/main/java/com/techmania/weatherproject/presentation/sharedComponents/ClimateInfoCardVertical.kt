package com.techmania.weatherproject.presentation.sharedComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

@Composable
fun ClimateInfoCardVertical(
    imageResourceId: Int,
    textResourceId: Int,
    amount: Double,
    unitResourceId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = Modifier.padding(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            ImageWithShadow(
                imageResource = imageResourceId,
                contentDescription = textResourceId,
                shadowColor = Color.LightGray,
                padding = 2.dp,
                modifier = modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier)
        Text(
            text = "$amount ${stringResource(unitResourceId)}",
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}