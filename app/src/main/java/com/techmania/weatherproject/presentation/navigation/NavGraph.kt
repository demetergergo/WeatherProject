package com.techmania.weatherproject.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.techmania.weatherproject.presentation.screens.forecastOverviewScreen.ForecastOverViewScreen
import com.techmania.weatherproject.presentation.screens.mainScreen.MainScreen
import com.techmania.weatherproject.presentation.screens.settingsScreen.SettingsScreen

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = Screen.Main.route){
        composable(route = Screen.Main.route){
            MainScreen(
                onNextSevenDaysClicked = { navController.navigate(Screen.ForecastOverview.route) },
                onSettingsClicked = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(route = Screen.ForecastOverview.route){
            ForecastOverViewScreen(onBackClicked = { navController.navigateUp() })
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(onBackClicked = { navController.navigateUp() })
        }
    }
}