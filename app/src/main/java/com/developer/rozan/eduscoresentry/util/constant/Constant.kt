package com.developer.rozan.eduscoresentry.util.constant

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager

object Constant {

    const val DELAY_INTENT: Long = 300L
    const val DELAY_LOAD_MORE: Long = 300L

    const val IS_FRAGMENT_HIDDEN = "isFragmentHidden"
    const val MAX_RETRIES = 3L

    private const val INITIAL_BACKOFF = 2000L
    fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF * (attempt + 1)


    fun toggleVisibility(
        isLoading: Boolean,
        shouldHide: Boolean = false,
        reverse: Boolean = false
    ) =
        when (isLoading) {
            true -> if (!reverse) View.VISIBLE else {
                if (shouldHide) View.INVISIBLE else View.GONE
            }
            false -> if (!reverse) {
                if (shouldHide) View.INVISIBLE else View.GONE
            } else View.VISIBLE
        }


    fun showKeyboard(activity: Activity, view: View) {
        val input =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }


    fun closeKeyboard(activity: Activity, view: View) {
        val input: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Suppress("DEPRECATION")
    fun isNetworkConnected(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }
        return result
    }
}