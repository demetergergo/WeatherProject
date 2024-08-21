package com.techmania.weatherproject.common.dependencyInjection

import com.techmania.weatherproject.data.permissions.location.DefaultLocationTracker
import com.techmania.weatherproject.data.permissions.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}