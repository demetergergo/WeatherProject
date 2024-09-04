package com.techmania.weatherproject.usecases

import android.content.Context
import android.location.Address
import android.os.Build
import androidx.annotation.RequiresApi
import com.techmania.weatherproject.data.permissions.location.parsing.LocationParse
import com.techmania.weatherproject.domain.models.LocationNameInfo
import javax.inject.Inject

class FetchLocationNameUseCase @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    operator fun invoke(
        latitude: Double,
        longitude: Double,
        context: Context,
    ): LocationNameInfo {
        val result = LocationParse.parseLocationFromCoordinates(latitude, longitude, context)
        return result.toLocationNameInfo()
    }
}

private fun Address.toLocationNameInfo(): LocationNameInfo {
    return LocationNameInfo(
        city = locality,
        country = countryName
    )
}
