package com.techmania.weatherproject.common.dependencyInjection

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.techmania.weatherproject.common.Constants
import com.techmania.weatherproject.data.networking.OpenMeteoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule{
    @Provides
    @Singleton
    fun retrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder().run {
            build()
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun openMeteoApi(retrofit: Retrofit): OpenMeteoApi {
        return retrofit.create(OpenMeteoApi::class.java)
    }

    @Provides
    @Singleton
    fun FusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

}