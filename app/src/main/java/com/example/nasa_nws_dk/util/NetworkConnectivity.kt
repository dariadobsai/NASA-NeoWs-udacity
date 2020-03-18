package com.example.nasa_nws_dk.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

// Solution for ConnectivityManager deprecated methods -> https://stackoverflow.com/questions/32547006/connectivitymanager-getnetworkinfoint-deprecated
fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.run {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            return hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
        }
    }
    return false
}
