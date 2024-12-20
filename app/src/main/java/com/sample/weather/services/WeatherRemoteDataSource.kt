package com.sample.weather.services

import android.util.Log
import com.sample.weather.model.CurrentWeather
import com.sample.weather.model.Error
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.net.URL
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor() {

    @OptIn(ExperimentalSerializationApi::class)
    fun getCurrentWeather(query: String): Result<CurrentWeather> = runCatching {
        val stream = URL("$CURRENT_WEATHER_URL$query").openStream()
        Json.decodeFromStream<CurrentWeather>(stream = stream)
    }.recoverCatching {
        Log.e("Exception in getCurrentWeather()", it.message, it)
        CurrentWeather(error = Error(message = NO_LOCATION_FOUND))
    }

    companion object {
        const val NO_LOCATION_FOUND = "No matching location found."
        private const val CURRENT_WEATHER_URL =
            "https://api.weatherapi.com/v1/current.json?key=94a417c35ca949a7a73154350241912&aqi=no&q="
    }
}


