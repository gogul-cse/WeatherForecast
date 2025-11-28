package com.application.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.application.jetweatherforecast.model.Favorite
import com.application.jetweatherforecast.model.Settings

@Database(entities = [Favorite::class,Settings::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}