package com.sample.weather.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import com.sample.weather.app.theme.ExtraLightGrayText
import com.sample.weather.app.theme.ExtraLightGrayText2
import com.sample.weather.app.theme.WeatherTheme
import com.sample.weather.ui.CurrentWeatherUiState

@Composable
fun WeatherDetails(
    state: CurrentWeatherUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            if (state.icon != null) {
                AsyncImage(
                    modifier = Modifier.size(size = 123.dp),
                    model = state.icon,
                    contentDescription = stringResource(id = R.string.weather_condition),
                )
            }
            if (state.name != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 26.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    content = {
                        Text(
                            text = state.name,
                            maxLines = 1,
                            fontSize = 30.sp,
                            color = colorScheme.onSurface,
                            fontWeight = FontWeight.W600,
                            style = typography.bodyMedium,
                            lineHeight = TextUnit(value = 45f, type = TextUnitType.Sp),
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_right),
                            contentDescription = stringResource(id = R.string.location),
                        )
                    }
                )
            }
            if (state.temp != null) {
                Text(
                    text = "${state.temp}\u00B0",
                    maxLines = 1,
                    fontSize = 70.sp,
                    color = colorScheme.onSurface,
                    fontWeight = FontWeight.W500,
                    style = typography.bodyMedium,
                    lineHeight = TextUnit(value = 105f, type = TextUnitType.Sp),
                )
            }
            if (state.humidity != null || state.uv != null || state.feelsLike != null) {
                WeatherInfo(
                    modifier = Modifier.padding(top = 26.dp),
                    state = state
                )
            }
        }
    )
}

@Composable
private fun WeatherInfo(
    modifier: Modifier = Modifier,
    state: CurrentWeatherUiState
) {
    Row(
        modifier = modifier
            .height(75.dp)
            .padding(horizontal = 56.dp)
            .background(color = ExtraLightGray, shape = RoundedCornerShape(16.dp))
            .padding(all = 16.dp),
        content = {
            if (state.humidity != null) {
                WeatherInfoItem(text = R.string.humidity, value = "${state.humidity}\u0025")
            }
            Spacer(modifier = Modifier.weight(1f))
            if (state.uv != null) {
                WeatherInfoItem(text = R.string.uv, value = state.uv)
            }
            Spacer(modifier = Modifier.weight(1f))
            if (state.feelsLike != null) {
                WeatherInfoItem(text = R.string.feels_like, value = "${state.feelsLike}\u00B0")
            }
        }
    )
}

@Composable
private fun WeatherInfoItem(
    @StringRes text: Int = R.string.humidity,
    value: String = "20%",
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(
                text = stringResource(text),
                maxLines = 1,
                fontSize = 12.sp,
                color = ExtraLightGrayText,
                fontWeight = FontWeight.W500,
                style = typography.bodyMedium,
                lineHeight = TextUnit(value = 18f, type = TextUnitType.Sp),
            )
            Text(
                text = value,
                maxLines = 1,
                fontSize = 15.sp,
                color = ExtraLightGrayText2,
                fontWeight = FontWeight.W500,
                style = typography.bodyMedium,
                lineHeight = TextUnit(value = 22.5f, type = TextUnitType.Sp),
            )
        }
    )
}

@Preview
@Composable
private fun MediaCarouselComponentPreview(
    @PreviewParameter(WeatherDetailsPreviewProvider::class) state: CurrentWeatherUiState,
) = WeatherTheme { WeatherDetails(state = state) }


private class WeatherDetailsPreviewProvider(
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
