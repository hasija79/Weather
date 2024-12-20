package com.sample.weather.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.weather.R
import com.sample.weather.app.theme.ExtraLightGray
import com.sample.weather.app.theme.WeatherTheme

@Composable
fun SearchBar(
    query: String,
    onSearch: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
//    var searchQuery by remember { mutableStateOf(query) }
    OutlinedTextField(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        value = query,
        singleLine = true,
        shape = RoundedCornerShape(size = 16.dp),
        onValueChange = onValueChange,//{ searchQuery = it },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                if (query.isNotEmpty()) {
                    onSearch(query)
                }
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = ExtraLightGray,
            focusedContainerColor = ExtraLightGray,
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        placeholder = {
            Text(
                style = typography.bodyLarge,
                text = stringResource(id = R.string.search_location_hint)
            )
        },
    )
}

@Preview
@Composable
private fun SearchBarPreview() = WeatherTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        content = {
            SearchBar(query = "Mumbai")
        }
    )
}
