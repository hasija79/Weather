package com.sample.weather.ui

import com.sample.weather.model.CurrentWeather


fun CurrentWeather.toWeatherUiState() = CurrentWeatherUiState(
    name = location?.name,
    uv = current?.uv.toString(),
    temp = current?.tempF.toString(),
    humidity = current?.humidity.toString(),
    feelsLike = current?.feelslikeF.toString(),
    icon = current?.condition?.icon?.let { "https:$it" },
)
