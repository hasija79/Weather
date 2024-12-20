package com.sample.weather.services

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
) {
    suspend fun getCurrentWeather(query: String) = withContext(Dispatchers.IO) {
        remoteDataSource.getCurrentWeather(query = query)
            .also { if (DEBUG) Log.d(TAG, "Response:  $it") }
    }

    companion object {
        private const val DEBUG = true
        private const val TAG = "WeatherRepository"
    }
}
