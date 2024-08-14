package com.techmania.weatherproject.data.permissions.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationInfoDto?
}