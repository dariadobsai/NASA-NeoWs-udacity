package com.example.nasa_nws_dk.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData

open class NetworkConnectivity(val context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    private val builder: NetworkRequest.Builder = NetworkRequest.Builder()

    val networkCallback = connectivityManager?.registerNetworkCallback(

        builder.build(),
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                if (isOnline())
                    postValue(true)

            }

            override fun onLost(network: Network) {
                postValue(false)
            }

        })

    // Solution for ConnectivityManager deprecated methods -> https://stackoverflow.com/questions/32547006/connectivitymanager-getnetworkinfoint-deprecated
    open fun isOnline(): Boolean {
        connectivityManager?.run {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                return hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                    NetworkCapabilities.TRANSPORT_CELLULAR
                )
            }
        }
        return false
    }
}


