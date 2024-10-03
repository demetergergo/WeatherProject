package com.techmania.weatherproject.presentation.settingsScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.techmania.weatherproject.presentation.dataStore.MyDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SettingsScreenViewModel @Inject constructor() : ViewModel() {
    var isDarkMode = MutableStateFlow<Boolean>(false)
    var isBackButtonEnabled = mutableStateOf(true)
    var showBottomSheet = mutableStateOf(false)


    suspend fun toggleTheme(context: Context) {
        MyDataStore.save("darkMode", (!isDarkMode.value).toString(), context)
        isDarkMode.value = MyDataStore.load("darkMode", context).toBoolean()
        Toast.makeText(context, "Theme changed ${isDarkMode.value}", Toast.LENGTH_SHORT).show()
    }

    suspend fun loadTheme(context: Context) {
        isDarkMode.value = MyDataStore.load("darkMode", context).toBoolean()
    }
}
