package com.sample.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.sample.weather.R
import com.sample.weather.app.theme.ExtraLightGray
import com.sample.weather.app.theme.WeatherTheme
import com.sample.weather.ui.CurrentWeatherUiState


@Composable
fun SearchResult(
    state: CurrentWeatherUiState,
    onClick: (CurrentWeatherUiState) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .background(color = ExtraLightGray, shape = RoundedCornerShape(16.dp))
            .clickable { onClick(state) }
            .padding(all = 16.dp),
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    if (state.name != null) {
                        Text(
                            text = state.name,
                            maxLines = 1,
                            fontSize = 20.sp,
                            color = colorScheme.onSurface,
                            fontWeight = FontWeight.W600,
                            style = typography.bodyMedium,
                            lineHeight = TextUnit(value = 30f, type = TextUnitType.Sp),
                        )
                    }
                    if (state.temp != null) {
                        Text(
                            maxLines = 1,
                            fontSize = 25.sp,
                            text = "${state.temp}\u00B0",
                            color = colorScheme.onSurface,
                            fontWeight = FontWeight.W500,
                            style = typography.bodyMedium,
                            lineHeight = TextUnit(value = 20f, type = TextUnitType.Sp),
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.weight(weight = 1f))
            if (state.icon != null) {
                AsyncImage(
                    modifier = Modifier.size(width = 83.dp, height = 67.dp),
                    model = state.icon,
                    contentDescription = stringResource(id = R.string.weather_condition),
                )
            }
        }
    )
}

@Preview
@Composable
private fun MediaCarouselComponentPreview(
    @PreviewParameter(SearchResultPreviewProvider::class) state: CurrentWeatherUiState,
) = WeatherTheme { SearchResult(state = state) }


private class SearchResultPreviewProvider(
    override val values: Sequence<CurrentWeatherUiState> = sequenceOf(
        CurrentWeatherUiState(
            name = "Mumbai",
            uv = "4",
            temp = "20.0",
            humidity = "20",
            feelsLike = "38",
            icon = "https://cdn.weatherapi.com/weather/64x64/day/143.png"
        )
    )
) : PreviewParameterProvider<CurrentWeatherUiState>
