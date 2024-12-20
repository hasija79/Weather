package com.sample.weather.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.weather.services.WeatherRepository
import com.sample.weather.util.ConnectivityUtil
import com.sample.weather.util.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val preferences: Preferences,
    private val connectivityUtil: ConnectivityUtil,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    var uiState: WeatherUiState by mutableStateOf(value = WeatherUiState.Empty)
        private set

    init {
        val selectedCity = preferences.getSearchQuery()
        if (selectedCity.isNotEmpty()) {
            getCurrentWeather(query = selectedCity)
        }
    }

    fun getCurrentWeatherDetails(state: CurrentWeatherUiState) {
        uiState = if (connectivityUtil.isConnected()) {
            WeatherUiState.WeatherDetails(result = state)
        } else {
            WeatherUiState.Error(message = NO_INTERNET_FOUND)
        }
    }

    fun getCurrentWeather(query: String) {
        if (connectivityUtil.isConnected()) {
            uiState = WeatherUiState.Loading(query = query)
            viewModelScope.launch {
                preferences.setSearchQuery(query = query)
                uiState = weatherRepository.getCurrentWeather(query = query).fold(
                    onFailure = { WeatherUiState.Error(message = it.message) },
                    onSuccess = {
                        if (it.error != null) {
                            WeatherUiState.Error(message = it.error?.message ?: NO_LOCATION_FOUND)
                        } else {
                            WeatherUiState.SearchResult(result = it.toWeatherUiState())
                        }
                    },
                )
            }
        } else {
            uiState = WeatherUiState.Error(message = NO_INTERNET_FOUND)
        }
    }

    companion object {
        const val NO_INTERNET_FOUND = "No Internet Found."
        const val NO_LOCATION_FOUND = "No matching location found."
    }
}
