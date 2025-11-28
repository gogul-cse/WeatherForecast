package com.application.jetweatherforecast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.application.jetweatherforecast.model.Favorite
import com.application.jetweatherforecast.model.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM fav_tbl")
    fun getFavourites(): Flow<List<Favorite>>

    @Query("SELECT * FROM fav_tbl WHERE city =:city")
    fun getFavById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("DELETE FROM fav_tbl")
    suspend fun deleteAllFavorite()

    //Unit
    @Query("SELECT * FROM settings_tbl")
    fun getUnits(): Flow<List<Settings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(settings: Settings)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(settings: Settings)

    @Delete
    suspend fun deleteUnit(settings: Settings)

    @Query("DELETE FROM settings_tbl")
    suspend fun deleteAllUnit()
}