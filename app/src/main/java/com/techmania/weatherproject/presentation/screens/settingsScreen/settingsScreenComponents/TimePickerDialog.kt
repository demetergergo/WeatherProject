package com.techmania.weatherproject.presentation.screens.settingsScreen.settingsScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.techmania.weatherproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
    timePickerState: TimePickerState,
) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(
                state = timePickerState, layoutType = TimePickerLayoutType.Vertical
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(onClick = onDismiss, modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(stringResource(id = R.string.cancel))
                }
                Button(modifier = Modifier.padding(horizontal = 8.dp), onClick = {
                    onConfirm(timePickerState.hour, timePickerState.minute)
                    onDismiss()
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            }
        }
    }
}