package com.sample.weather.util

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectivityUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    fun isConnected() = with(connectivityManager) {
        activeNetwork != null && getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

}
