package com.techmania.weatherproject.presentation.screens.settingsScreen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.techmania.weatherproject.R
import com.techmania.weatherproject.domain.models.AlarmScheduler
import com.techmania.weatherproject.presentation.alarmManager.AndroidAlarmScheduler
import com.techmania.weatherproject.presentation.screens.settingsScreen.settingsScreenComponents.SettingsItem
import com.techmania.weatherproject.presentation.screens.settingsScreen.settingsScreenComponents.TimePickerDialog
import com.techmania.weatherproject.presentation.sharedComponents.ExpandableCard
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val isDarkMode = settingsScreenViewModel.isDarkMode.collectAsState()
    val currentTime = settingsScreenViewModel.currentTime
    val calendarState = settingsScreenViewModel.calendarState
    val alarmItem = settingsScreenViewModel.alarmItem
    val secondsText = settingsScreenViewModel.secondsText
    val notificationSwitchState = settingsScreenViewModel.notificationSwitchState.collectAsState()
    val hasNotificationPermission = settingsScreenViewModel.hasNotificationPermission
    val postNotificationPermissionState = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS
    )

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )
    val scheduler: AlarmScheduler = AndroidAlarmScheduler(context)
    //end

    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.hu to "hu",
    ).mapKeys { stringResource(it.key) }

    val showBottomSheet = settingsScreenViewModel.showBottomSheet
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        settingsScreenViewModel.loadSettings(context)
    }
    LaunchedEffect(postNotificationPermissionState.status) {
        hasNotificationPermission.value = postNotificationPermissionState.status.isGranted
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.settings)) },
            navigationIcon = {
                IconButton(onClick = {
                    onBackClicked()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
        )
    }) { innerPadding ->
        if (calendarState.value) {
            Dialog(
                onDismissRequest = { settingsScreenViewModel.toggleCalendarState() },
                properties = DialogProperties(usePlatformDefaultWidth = true)
            ) {
                TimePickerDialog(
                    onConfirm = { hour, minute ->
                        settingsScreenViewModel.confirmAlarm(secondsText, hour, minute, scheduler)
                    },
                    onDismiss = { settingsScreenViewModel.toggleCalendarState() },
                    timePickerState = timePickerState,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (showBottomSheet.value) {
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet.value = false
                    }, sheetState = sheetState
                ) {
                    localeOptions.keys.forEach { locale ->
                        OutlinedButton(
                            onClick = {
                                AppCompatDelegate.setApplicationLocales(
                                    LocaleListCompat.forLanguageTags(
                                        localeOptions[locale]
                                    )
                                )
                            },
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                        ) {
                            Text(text = locale)
                        }
                    }
                }
            }

            SettingsItem(settingsTextResource = R.string.dark_mode, settingChanger = {
                Switch(checked = isDarkMode.value, onCheckedChange = {
                    coroutineScope.launch {
                        settingsScreenViewModel.toggleTheme(context)
                    }
                }, modifier = Modifier)
            })
            SettingsItem(settingsTextResource = R.string.language, settingChanger = {
                Button(onClick = { showBottomSheet.value = !showBottomSheet.value }) {
                    Text(text = Locale.getDefault().displayLanguage)
                }
            })
            ExpandableCard(
                expandedState = notificationSwitchState.value,
                onClick = { /*TODO*/ },
                overView = {
                    SettingsItem(settingsTextResource = R.string.notifications, settingChanger = {
                        Switch(checked = notificationSwitchState.value, onCheckedChange = {
                            coroutineScope.launch {
                                settingsScreenViewModel.toggleNotificationSwitchState(context)
                            }
                        })
                    })
                },
                details = {
                    if (hasNotificationPermission.value) {
                        SettingsItem(
                            settingsTextResource = R.string.notifications,
                            settingChanger = {
                                Button(onClick = { settingsScreenViewModel.toggleCalendarState() }) {
                                    Text(text = stringResource(id = R.string.notifications))
                                }
                            })
                    } else {
                        Text(
                            text = stringResource(id = R.string.permission_denied),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                })
        }
    }
}



