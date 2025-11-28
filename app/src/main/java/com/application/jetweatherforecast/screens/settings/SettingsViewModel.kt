package com.application.jetweatherforecast.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.jetweatherforecast.model.Settings
import com.application.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val repository: WeatherDbRepository): ViewModel() {
    private val _settingsList = MutableStateFlow<List<Settings>>(emptyList())
    val unitList = _settingsList.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnit().distinctUntilChanged()
                .collect { listOfFavs->
                    if (listOfFavs.isEmpty()){

                    }else{
                        _settingsList.value = listOfFavs
                    }
                }
        }
    }

    fun insertUnit(settings: Settings)
            = viewModelScope.launch { repository.insertUnit(settings)}

    fun updateUnit(settings: Settings)
            = viewModelScope.launch { repository.updateUnit(settings)}

    fun deleteUnit(settings: Settings)
            = viewModelScope.launch { repository.deleteUnit(settings)}

    fun deleteAllUnit() = viewModelScope.launch { repository.deleteAllUnit() }
}