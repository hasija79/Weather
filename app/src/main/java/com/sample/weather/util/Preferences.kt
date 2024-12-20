package com.sample.weather.util

import android.content.Context
import com.sample.weather.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Preferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPref = with(context) {
        getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

    fun setSearchQuery(query: String) = setValue(key = KEY_SEARCH_QUERY, value = query)

    fun getSearchQuery() = sharedPref.getString(KEY_SEARCH_QUERY, "").orEmpty()

    private fun setValue(key: String, value: Any) {
        with(sharedPref.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw IllegalArgumentException("Unsupported value type")
            }
            apply()
        }
    }

    companion object {
        const val KEY_SEARCH_QUERY = "search_query"
    }
}
