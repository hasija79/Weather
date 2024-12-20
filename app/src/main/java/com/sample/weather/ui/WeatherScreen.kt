package com.sample.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sample.weather.R
import com.sample.weather.app.theme.WeatherTheme
import com.sample.weather.ui.components.SearchBar
import com.sample.weather.ui.components.SearchResult
import com.sample.weather.ui.components.WeatherDetails

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    WeatherPage(
        modifier = modifier,
        state = viewModel.uiState,
        onSearch = viewModel::getCurrentWeather,
        onSearchResultClick = viewModel::getCurrentWeatherDetails,
    )
}

@Composable
private fun WeatherPage(
    modifier: Modifier = Modifier,
    state: WeatherUiState,
    onSearch: (String) -> Unit = {},
    onSearchResultClick: (CurrentWeatherUiState) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorScheme.onPrimary)
            .padding(top = 44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            var query by remember { mutableStateOf(state.query) }
            SearchBar(
                query = query,
                onSearch = onSearch,
                onValueChange = { query = it },
            )
            when (state) {
                is WeatherUiState.Empty -> WeatherEmpty()
                is WeatherUiState.Loading -> WeatherLoading()
                is WeatherUiState.Error -> WeatherError(state = state)
                is WeatherUiState.WeatherDetails -> WeatherDetails(state = state.result)
                is WeatherUiState.SearchResult ->
                    SearchResult(state = state.result, onClick = onSearchResultClick)
            }
        }
    )
}

@Composable
private fun WeatherLoading() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        content = {
            Text(
                fontSize = 24.sp,
                color = colorScheme.primary,
                style = typography.bodyLarge,
                text = stringResource(R.string.loading_msg),
            )
            CircularProgressIndicator()
        }
    )
}

@Composable
private fun WeatherEmpty() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.W600,
                style = typography.bodyLarge,
                lineHeight = TextUnit(value = 45f, TextUnitType.Sp),
                text = stringResource(R.string.no_city_selected),
            )
            Text(
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                style = typography.bodyMedium,
                lineHeight = TextUnit(value = 22.5f, TextUnitType.Sp),
                text = stringResource(R.string.search_for_city),
            )
        }
    )
}

@Composable
private fun WeatherError(
    state: WeatherUiState.Error,
) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        fontSize = 24.sp,
        style = typography.bodyMedium,
        text = state.message ?: stringResource(R.string.error_msg),
    )
}

@Stable
sealed interface WeatherUiState {
    data object Empty : WeatherUiState
    data class Error(val message: String?) : WeatherUiState
    data class Loading(val query: String? = null) : WeatherUiState
    data class SearchResult(val result: CurrentWeatherUiState) : WeatherUiState
    data class WeatherDetails(val result: CurrentWeatherUiState) : WeatherUiState
}

@Stable
data class CurrentWeatherUiState(
    val temp: String? = null,
    val name: String? = null,
    val icon: String? = null,
    val uv: String? = null,
    val humidity: String? = null,
    val feelsLike: String? = null,
)

private val WeatherUiState.query
    get() = (this as? WeatherUiState.Loading)?.query.orEmpty()

@Preview
@Composable
private fun MediaCarouselComponentPreview(
    @PreviewParameter(WeatherPreviewProvider::class) state: WeatherUiState,
) = WeatherTheme { WeatherPage(state = state) }


private class WeatherPreviewProvider(
    override val values: Sequence<WeatherUiState> = sequenceOf(
        WeatherUiState.Empty,
        WeatherUiState.SearchResult(
            result = CurrentWeatherUiState(
                name = "Mumbai",
                temp = "20.0",
                icon = "https://cdn.weatherapi.com/weather/64x64/day/143.png"
            )
        ),
        WeatherUiState.WeatherDetails(
            result = CurrentWeatherUiState(
                name = "Mumbai",
                temp = "20.0",
                icon = "https://cdn.weatherapi.com/weather/64x64/day/143.png"
            )
        ),
        WeatherUiState.Error(message = "No matching location found."),
        WeatherUiState.Loading(query = "Mumbai"),
    ),
) : PreviewParameterProvider<WeatherUiState>
