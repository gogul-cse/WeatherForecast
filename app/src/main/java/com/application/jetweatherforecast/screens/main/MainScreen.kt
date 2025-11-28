package com.application.jetweatherforecast.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.jetweatherforecast.data.DataOrException
import com.application.jetweatherforecast.model.Weather
import com.application.jetweatherforecast.model.WeatherItem
import com.application.jetweatherforecast.navigation.WeatherScreens
import com.application.jetweatherforecast.screens.settings.SettingsViewModel
import com.application.jetweatherforecast.utils.formatDate
import com.application.jetweatherforecast.utils.formatDecimals
import com.application.jetweatherforecast.widgets.HumidityWindPressureRow
import com.application.jetweatherforecast.widgets.SunRiseSunSetRow
import com.application.jetweatherforecast.widgets.WeatherAppBar
import com.application.jetweatherforecast.widgets.WeatherDetailRow
import com.application.jetweatherforecast.widgets.WeatherStateImage

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val curCity: String = if (city.isNullOrBlank()) "Seattle" else city

    val unitFromDb by settingsViewModel.unitList.collectAsState()
    val unit = unitFromDb.firstOrNull()?.unit?.split(" ")?.get(0)?.lowercase()
        ?: "imperial"

    val isImperial = unit == "imperial"

    val weatherData by produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true),
        key1 = unit
    ) {
        value = mainViewModel.getWeatherData(curCity, unit)
    }

    when {
        weatherData.loading == true -> {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        }

        weatherData.data != null -> {
            MainScaffold(
                weather = weatherData.data!!,
                navController = navController,
                isImperial = isImperial
            )
        }

        weatherData.e != null -> {
            Text(
                text = "Error loading weather: ${weatherData.e?.message}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + " ,${weather.city.country}",
            navController = navController, onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 3.dp)
    }) {innerPadding->
        MainContent(data = weather,isImperial = isImperial, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun MainContent(data: Weather, modifier: Modifier = Modifier, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
    Column(modifier = modifier.padding(4.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = formatDate( weatherItem.dt),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier.padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                //Image
                WeatherStateImage(imageUrl= imageUrl)
                Text(text = formatDecimals( weatherItem.temp.day)+"°",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = data.list[0],isImperial = isImperial)
        HorizontalDivider()
        SunRiseSunSetRow(weather = data.list[0])
        Text(text = "This Week", fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium)
        Surface(modifier= Modifier.fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)) {
                items(items = data.list){item:WeatherItem->
                    WeatherDetailRow(weather = item)
                }
            }
        }

    }
}


