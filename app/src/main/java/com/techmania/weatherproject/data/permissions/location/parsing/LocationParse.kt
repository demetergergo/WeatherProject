package com.techmania.weatherproject.data.permissions.location.parsing

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Locale

object LocationParse {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)

    fun parseLocationFromCoordinates(
        latitude: Double,
        longitude: Double,
        context: Context,
    ): Address {
        val result =
            Geocoder(context, Locale.getDefault()).getFromLocation(latitude, longitude, 1)?.first()
                ?: throw Exception("Location not found")
        return result
    }
}