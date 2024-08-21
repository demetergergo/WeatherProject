package com.techmania.weatherproject.data.permissions.location

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationInfoDto?
}