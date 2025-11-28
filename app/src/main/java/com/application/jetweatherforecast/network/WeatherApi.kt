package com.application.jetweatherforecast.network

import com.application.jetweatherforecast.model.Weather
import com.application.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid")addid: String = Constants.API_KEY
    ): Weather
}