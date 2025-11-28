package com.application.jetweatherforecast.repository

import com.application.jetweatherforecast.model.Settings
import com.application.jetweatherforecast.data.WeatherDao
import com.application.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {
    fun getFavorite(): Flow<List<Favorite>> = weatherDao.getFavourites()
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorite()
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)

    //Unit
    fun getUnit(): Flow<List<Settings>> = weatherDao.getUnits()
    suspend fun insertUnit(settings:Settings) = weatherDao.insertUnit(settings)
    suspend fun updateUnit(settings: Settings) = weatherDao.updateUnit(settings)
    suspend fun deleteUnit(settings: Settings) = weatherDao.deleteUnit(settings)
    suspend fun deleteAllUnit() = weatherDao.deleteAllUnit()
}