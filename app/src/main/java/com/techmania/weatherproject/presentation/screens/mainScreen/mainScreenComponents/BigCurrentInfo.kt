package com.techmania.weatherproject.presentation.screens.mainScreen.mainScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.models.WeatherInfo
import com.techmania.weatherproject.presentation.sharedComponents.ImageWithShadow
import java.time.format.DateTimeFormatter

@Composable
fun BigCurrentInfo(
    weatherInfoCurrent: WeatherInfo,
    city: String,
    country: String,
    chipText: Int,
    toggleChipOnClick: () -> Unit,
    toggleChipOnSelected: Boolean,
    modifier: Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "$city,", fontSize = 35.sp)
        Text(text = country, fontSize = 35.sp)
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = weatherInfoCurrent.time.format(DateTimeFormatter.ofPattern("EEE, MMM dd HH:mm")))
            Spacer(modifier = Modifier.weight(1f))
            ElevatedFilterChip(
                selected = toggleChipOnSelected,
                onClick = toggleChipOnClick,
                label = { Text(text = stringResource(chipText))},
                leadingIcon = if (toggleChipOnSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Refresh icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                },
            )
        }
    }
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageWithShadow(
            weatherInfoCurrent.iconRes,
            weatherInfoCurrent.weatherDesc,
            shadowColor = MaterialTheme.colorScheme.surfaceDim,
            padding = 5.dp,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier) {
                Text(text = weatherInfoCurrent.temperature.toString(), fontSize = 60.sp)
                Column(
                    verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start
                ) {
                    Text(text = stringResource(id = R.string.unit_celsius), fontSize = 20.sp)
                }
            }
            Text(text = stringResource(id = weatherInfoCurrent.weatherDesc), fontSize = 25.sp)
        }
        Spacer(modifier = Modifier.weight(1.7f))
    }
}