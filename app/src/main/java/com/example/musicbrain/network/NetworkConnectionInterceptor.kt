package com.example.musicbrain.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * Interceptor class to check network connectivity before making network requests.
 *
 * @param context The application context.
 */
class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
    /**
     * Intercepts the network request and checks for network connectivity.
     *
     * @param chain The interceptor chain.
     * @return The response if there is connectivity, otherwise an IOException is thrown.
     */
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.run {
            if (!isConnected(context = context)) {
                Log.i("retrofit", "there is no connection")
                throw IOException()
            } else {
                val builder = chain.request().newBuilder()
                return@run chain.proceed(builder.build())
            }
        }

    /**
     * Checks if the device is connected to a network.
     *
     * @param context The application context.
     * @return `true` if the device is connected, otherwise `false`.
     */
    private fun isConnected(context: Context): Boolean {
        val result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result =
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        return result
    }
}
