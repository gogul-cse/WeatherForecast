package com.application.jetweatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.application.jetweatherforecast.data.DataOrException
import com.application.jetweatherforecast.model.Weather
import com.application.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository): ViewModel(){

    suspend fun getWeatherData(city: String, units: String): DataOrException<Weather, Boolean, Exception>{
        return repository.getWeather(cityQuery = city,units = units)
    }
}