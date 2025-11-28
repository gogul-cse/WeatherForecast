package com.application.jetweatherforecast.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.application.jetweatherforecast.model.Settings
import com.application.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavController,settingsViewModel: SettingsViewModel= hiltViewModel()){
    val measurementUnits = listOf("Imperial (F)","Metric (C)")
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val currentUnit = choiceFromDb.firstOrNull()?.unit?:"imperial"
    var unitToggle by remember { mutableStateOf(currentUnit == "metric") }
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            icon = Icons.AutoMirrored.Default.ArrowBack,
            isMainScreen = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {innerPadding->
        Surface(modifier = Modifier.padding(innerPadding)
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp))

                IconToggleButton(checked = unitToggle, onCheckedChange = {
                    unitToggle = it
                }, modifier = Modifier.fillMaxWidth(0.5f)
                    .clip(shape = RectangleShape)
                    .padding(5.dp)
                    .background(Color.Magenta.copy(alpha = 0.4f))){
                    Text(text = if (!unitToggle) "Fahrenheit °F" else "Celsius °C")
                }
                Button(onClick = {
                    settingsViewModel.deleteAllUnit()
                    val unitSelected = if (unitToggle) "metric" else "imperial"
                    settingsViewModel.insertUnit(Settings(unit = unitSelected))

                    navController.popBackStack()
                },
                    modifier = Modifier.padding(8.dp),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEFBE42))
                ) {
                    Text(text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 18.sp)
                }
            }
        }
    }
}