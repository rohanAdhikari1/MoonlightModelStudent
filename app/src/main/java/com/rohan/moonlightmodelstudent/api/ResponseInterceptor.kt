package com.rohan.moonlightmodelstudent.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.InetAddress

class ResponseInterceptor(private val mContext: Context) :
    Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isConnected) {
            throw NoConnectivityException()
        }
        val token = Tokenizer.getEncryptedShredPrefs(mContext).getString("token","").toString()
//        if(token.isNotEmpty()){
            val builder: Request.Builder = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
            val response = chain.proceed(builder.build())
        val contentType = response.body()?.contentType()
        if (contentType != null && contentType.type() == "application" && contentType.subtype() == "json") {
            val modifiedResponse = response.newBuilder()
//                .body(decodeJsonResponse(response.body()))
                .build()
            return modifiedResponse
        }

        return response
//        } else {
//            return
//        }
    }
//    private fun decodeJsonResponse(responseBody: ResponseBody?): ResponseBody {
//        val json = responseBody?.string()
//        if (json != null) {
//            try {
//                val decodedData = gson.fromJson(json, YourDataType::class.java)
//
//                // Convert the decoded data back to a JSON string
//                val decodedJson = gson.toJson(decodedData)
//
//                return ResponseBody.create(MediaType.parse("application/json"), decodedJson)
//            } catch (e: Exception) {
//                throw IOException("Something went wrong")
//            }
//        }
//        return responseBody ?: ResponseBody.create(null, "")
//    }
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