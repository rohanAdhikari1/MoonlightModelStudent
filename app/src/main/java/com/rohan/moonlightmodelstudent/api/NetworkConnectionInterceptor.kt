package com.rohan.moonlightmodelstudent.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.InetAddress


class NetworkConnectionInterceptor(private val mContext: Context) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        if (!isConnected) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = request.newBuilder()
        return chain.proceed(builder.build())
    }
    init{
        System.loadLibrary("keys")
    }
    private external fun getApiname():String

    val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            val activenet =  when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
            }
            return if (activenet){
                try {
                    InetAddress.getByName(getApiname()).isReachable(3000)
                } catch (e: IOException) {
                    false
                }
            }else{
                false
            }
        }
}