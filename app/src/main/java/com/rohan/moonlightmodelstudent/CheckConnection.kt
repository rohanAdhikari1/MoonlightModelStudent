package com.rohan.moonlightmodelstudent

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetAddress


class CheckConnection(private val connectivityManager: ConnectivityManager) : MutableLiveData<Int>() {
    constructor(application: Application) : this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    private val networkCallback = object : ConnectivityManager.NetworkCallback(){

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            if (hasInternetCapability == true) {
                    if (netwowkceck()) {
                        postValue(1)
                    }else{
                        postValue(2)
                }
            }else{
                postValue(2)
            }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            postValue(3)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(4)
        }

    }

    override fun onActive() {
        super.onActive()
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities == null){
            postValue(3)
        }
        val builder = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    private fun netwowkceck():Boolean{
//        val command = "ping -c 1 google.com"
//        try {
//            if (Runtime.getRuntime().exec(command).waitFor() == 0){
//                postValue(1)
//                Log.e("@@@","n1")
//            }else{
//                postValue(2)
//                Log.e("@@@","un1")
//            }
//        } catch (e: InterruptedException) {
//            postValue(2)
//            Log.e("@@@","un2")
//        } catch (e: IOException) { e.printStackTrace()
//            postValue(2)
//            Log.e("@@@","un3")
//        }
        return try {
            InetAddress.getByName("www.example.com").isReachable(1000)
        } catch (e: IOException) {
            false
        }
    }
}