package com.techmania.weatherproject.usecases

import androidx.annotation.DrawableRes
import com.techmania.weatherproject.R

sealed class WeatherType(
    val weatherDesc: Int,
    @DrawableRes val iconRes: Int
) {
    object ClearSky : WeatherType(
        weatherDesc = R.string.clear_sky,
        iconRes = R.drawable.sunny
    )
    object MainlyClear : WeatherType(
        weatherDesc = R.string.mainly_clear,
        iconRes = R.drawable.cloudy
    )
    object PartlyCloudy : WeatherType(
        weatherDesc = R.string.partly_cloudy,
        iconRes = R.drawable.cloudy
    )
    object Overcast : WeatherType(
        weatherDesc = R.string.overcast,
        iconRes = R.drawable.very_cloudy
    )
    object Foggy : WeatherType(
        weatherDesc = R.string.foggy,
        iconRes = R.drawable.very_cloudy
    )
    object DepositingRimeFog : WeatherType(
        weatherDesc = R.string.depositing_rime_fog,
        iconRes = R.drawable.very_cloudy
    )
    object LightDrizzle : WeatherType(
        weatherDesc = R.string.light_drizzle,
        iconRes = R.drawable.rainy
    )
    object ModerateDrizzle : WeatherType(
        weatherDesc = R.string.moderate_drizzle,
        iconRes = R.drawable.rainy
    )
    object DenseDrizzle : WeatherType(
        weatherDesc = R.string.dense_drizzle,
        iconRes = R.drawable.rainy
    )
    object LightFreezingDrizzle : WeatherType(
        weatherDesc = R.string.light_freezing_drizzle,
        iconRes = R.drawable.snow
    )
    object DenseFreezingDrizzle : WeatherType(
        weatherDesc = R.string.dense_freezing_drizzle,
        iconRes = R.drawable.snow
    )
    object SlightRain : WeatherType(
        weatherDesc = R.string.slight_rain,
        iconRes = R.drawable.rainy
    )
    object ModerateRain : WeatherType(
        weatherDesc = R.string.moderate_rain,
        iconRes = R.drawable.heavy_rainy
    )
    object HeavyRain : WeatherType(
        weatherDesc = R.string.heavy_rain,
        iconRes = R.drawable.heavy_rainy
    )
    object HeavyFreezingRain: WeatherType(
        weatherDesc = R.string.heavy_freezing_rain,
        iconRes = R.drawable.snow
    )
    object SlightSnowFall: WeatherType(
        weatherDesc = R.string.slight_snow_fall,
        iconRes = R.drawable.snow
    )
    object ModerateSnowFall: WeatherType(
        weatherDesc = R.string.moderate_snow_fall,
        iconRes = R.drawable.snow
    )
    object HeavySnowFall: WeatherType(
        weatherDesc = R.string.heavy_snow_fall,
        iconRes = R.drawable.snow
    )
    object SnowGrains: WeatherType(
        weatherDesc = R.string.snow_grains,
        iconRes = R.drawable.snow
    )
    object SlightRainShowers: WeatherType(
        weatherDesc = R.string.slight_rain_showers,
        iconRes = R.drawable.rainy
    )
    object ModerateRainShowers: WeatherType(
        weatherDesc = R.string.moderate_rain_showers,
        iconRes = R.drawable.rainy
    )
    object ViolentRainShowers: WeatherType(
        weatherDesc = R.string.violent_rain_showers,
        iconRes = R.drawable.heavy_rainy
    )
    object SlightSnowShowers: WeatherType(
        weatherDesc = R.string.slight_snow_showers,
        iconRes = R.drawable.rainy
    )
    object HeavySnowShowers: WeatherType(
        weatherDesc = R.string.heavy_snow_showers,
        iconRes = R.drawable.rainy
    )
    object ModerateThunderstorm: WeatherType(
        weatherDesc = R.string.moderate_thunderstorm,
        iconRes = R.drawable.thunder
    )
    object SlightHailThunderstorm: WeatherType(
        weatherDesc = R.string.slight_hail_thunderstorm,
        iconRes = R.drawable.thunderstorm
    )
    object HeavyHailThunderstorm: WeatherType(
        weatherDesc = R.string.heavy_hail_thunderstorm,
        iconRes = R.drawable.thunderstorm
    )


    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                0 -> ClearSky
                1 -> MainlyClear
                2 -> PartlyCloudy
                3 -> Overcast
                45 -> Foggy
                48 -> DepositingRimeFog
                51 -> LightDrizzle
                53 -> ModerateDrizzle
                55 -> DenseDrizzle
                56 -> LightFreezingDrizzle
                57 -> DenseFreezingDrizzle
                61 -> SlightRain
                63 -> ModerateRain
                65 -> HeavyRain
                66 -> LightFreezingDrizzle
                67 -> HeavyFreezingRain
                71 -> SlightSnowFall
                73 -> ModerateSnowFall
                75 -> HeavySnowFall
                77 -> SnowGrains
                80 -> SlightRainShowers
                81 -> ModerateRainShowers
                82 -> ViolentRainShowers
                85 -> SlightSnowShowers
                86 -> HeavySnowShowers
                95 -> ModerateThunderstorm
                96 -> SlightHailThunderstorm
                99 -> HeavyHailThunderstorm
                else -> ClearSky
            }
        }
    }
}