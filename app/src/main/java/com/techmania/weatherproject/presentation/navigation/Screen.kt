package com.techmania.weatherproject.presentation.navigation

sealed class Screen(val route: String){
    object Main : Screen("main_screen")
    object ForecastOverview : Screen("forecast_overview_screen")
    object Settings : Screen("settings_screen")
}