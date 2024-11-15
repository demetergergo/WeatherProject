package com.techmania.weatherproject.presentation.screens.settingsScreen.settingsScreenComponents

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItem(settingsText: String, settingChanger: @Composable() () -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            // horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = settingsText, modifier = Modifier.weight(2f))
            Spacer(modifier = Modifier.weight(0.5f))
            settingChanger()
        }
    }
}