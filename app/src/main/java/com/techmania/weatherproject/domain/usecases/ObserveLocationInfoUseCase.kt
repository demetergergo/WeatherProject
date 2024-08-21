package com.techmania.weatherproject.domain.usecases

import com.techmania.weatherproject.data.permissions.location.LocationTracker
import com.techmania.weatherproject.domain.models.LocationInfo
import javax.inject.Inject

class ObserveLocationInfoUseCase @Inject constructor(
    private val locationTracker: LocationTracker
) {
    suspend operator fun invoke(): LocationInfo? {
        return locationTracker.getCurrentLocation()?.let{
            LocationInfo(it.latitude, it.longitude)
        }
    }
}