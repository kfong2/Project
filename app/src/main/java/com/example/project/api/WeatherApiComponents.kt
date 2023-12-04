package com.example.project.api


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun fetchTemperature(
    latitude: Double,
    longitude: Double,
    onSuccess: (Double) -> Unit,
    onError: () -> Unit,
    apiKey: String,
    units: String
) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            val api = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)

            val response = api.getCurrentWeather(latitude, longitude, apiKey, units)

            withContext(Dispatchers.Main) {
                onSuccess(response.main?.temp ?: 0.0)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("Temperature", "Failed to fetch current temperature: ${e.message}", e)
                onError()
            }
        }
    }
}

@Composable
fun TemperatureComponent(
    temperature: Double,
    isLoading: Boolean,
    apiKey: String,
    onRefresh: () -> Unit
) {
    var currentTemperature by remember { mutableStateOf(temperature) }
    var loading by remember { mutableStateOf(isLoading) }

    // Constants for latitude and longitude
    val latitude = 49.8844
    val longitude = -97.147

    LaunchedEffect(Unit) {
        fetchTemperature(
            latitude = latitude,
            longitude = longitude,
            onSuccess = { newTemperature ->
                // Update the state variable or perform any action with the new temperature
                currentTemperature = newTemperature
                loading = false
            },
            onError = {
                // Handle the case where fetching the temperature failed
                Log.e("Temperature", "Failed to fetch current temperature")
                loading = false
            },
            apiKey = apiKey,
            units = "metric"

        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cloud,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Current Temperature in Winnipeg: $currentTemperature °C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}



