package com.techmania.weatherproject.presentation.settingsScreen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.techmania.weatherproject.R
import com.techmania.weatherproject.presentation.settingsScreen.settingsScreenComponents.SettingsItem
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isDarkMode = settingsScreenViewModel.isDarkMode.collectAsState()
    val isBackButtonEnabled = settingsScreenViewModel.isBackButtonEnabled

    val localeOptions = mapOf(
        R.string.en to "en",
        R.string.hu to "hu",
    ).mapKeys { stringResource(it.key) }

    val showBottomSheet = settingsScreenViewModel.showBottomSheet
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        settingsScreenViewModel.loadTheme(context)
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.settings)) },
            navigationIcon = {
                IconButton(onClick = {
                    onBackClicked()
                    isBackButtonEnabled.value = false
                }, enabled = isBackButtonEnabled.value) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
        )
    }) { innerPadding ->
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
        }
    }
}