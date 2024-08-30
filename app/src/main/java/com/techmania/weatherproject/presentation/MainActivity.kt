package com.techmania.weatherproject.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.techmania.weatherproject.presentation.navigation.SetupNavGraph
import com.techmania.weatherproject.presentation.ui.theme.WeatherProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherProjectTheme(dynamicColor = false) {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}


