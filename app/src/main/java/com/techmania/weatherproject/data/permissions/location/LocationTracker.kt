package com.techmania.weatherproject.data.permissions.location

import com.techmania.weatherproject.data.permissions.location.dto.LocationInfoDto

interface LocationTracker {
    suspend fun getCurrentLocation(): LocationInfoDto?
}