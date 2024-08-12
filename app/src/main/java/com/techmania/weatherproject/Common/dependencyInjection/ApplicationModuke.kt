package com.techmania.weatherproject.Common.dependencyInjection

import com.techmania.weatherproject.Common.Constants
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
}



//                    retrofit()
//                    LaunchedEffect(Unit) {
//                        var weatherData = openMeteoApi(retrofit()).getWeatherData(
//                            47.1, 19.1
//                        )
//                        Toast.makeText(
//                            this@MainActivity,
//                            weatherData?.hourly?.temperature_2m?.getOrNull(0).toString() ?: "",
//                            Toast.LENGTH_LONG
//                        ).show()