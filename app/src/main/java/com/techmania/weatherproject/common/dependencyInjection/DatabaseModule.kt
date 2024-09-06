package com.techmania.weatherproject.common.dependencyInjection

import android.app.Application
import androidx.room.Room
import com.techmania.weatherproject.common.Constants
import com.techmania.weatherproject.data.database.MyRoomDatabase
import com.techmania.weatherproject.data.database.dao.WeatherInfoCurrentDao
import com.techmania.weatherproject.data.database.dao.WeatherInfoDailyDao
import com.techmania.weatherproject.data.database.dao.WeatherInfoHourlyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun myRoomDatabase(application: Application): MyRoomDatabase {
        return Room.databaseBuilder(
            application,
            MyRoomDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    fun weatherInfoCurrentDao(database: MyRoomDatabase): WeatherInfoCurrentDao {
        return database.weatherInfoCurrentDao
    }

    @Provides
    fun weatherInfoDailyDao(database: MyRoomDatabase): WeatherInfoDailyDao {
        return database.weatherInfoDailyDao
    }

    @Provides
    fun weatherInfoHourlyDao(database: MyRoomDatabase): WeatherInfoHourlyDao {
        return database.weatherInfoHourlyDao
    }
}