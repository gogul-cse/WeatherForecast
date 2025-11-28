package com.application.jetweatherforecast.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.jetweatherforecast.model.Favorite
import com.application.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorite().distinctUntilChanged()
                .collect { listOfFavs->
                    if (listOfFavs.isNullOrEmpty()){

                    }else{
                        _favList.value = listOfFavs
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite)
    = viewModelScope.launch { repository.insertFavorite(favorite)}

    fun updateFavorite(favorite: Favorite)
            = viewModelScope.launch { repository.updateFavorite(favorite)}

    fun deleteFavorite(favorite: Favorite)
            = viewModelScope.launch { repository.deleteFavorite(favorite)}
}