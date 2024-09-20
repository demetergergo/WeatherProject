package com.techmania.weatherproject.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.techmania.weatherproject.data.dataStore.MyDataStore
import com.techmania.weatherproject.presentation.navigation.SetupNavGraph
import com.techmania.weatherproject.presentation.ui.theme.WeatherProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val preferences: Flow<String> by lazy {
        MyDataStore.loadFlow("darkMode", this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val isDarkMode = runBlocking {
            MyDataStore.ensureInitialized("darkMode", "true", this@MainActivity)
            preferences.first().toBoolean()
        }
        setDarkMode(isDarkMode)
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            preferences.collect { value ->
                setDarkMode(value.toBoolean())
            }
        }
        enableEdgeToEdge()
        setContent {
            WeatherProjectTheme(dynamicColor = true) {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }

        }
    }
}

private fun setDarkMode(isDarkMode: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
    )
}



