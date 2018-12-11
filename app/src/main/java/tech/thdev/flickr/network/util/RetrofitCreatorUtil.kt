@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.network.util

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.thdev.flickr.BuildConfig
import java.util.concurrent.TimeUnit

private const val REQUEST_TIME_OUT = 60L

inline fun <T> createRetrofit(cls: Class<T>, baseUrl: String, noinline isInternetAvailable: () -> Boolean): T =
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient(isInternetAvailable))
                .build()
                .create(cls)

fun createOkHttpClient(isInternetAvailable: () -> Boolean): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                // println log tag name is "API"
                println(it)
            }).setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
            connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            addInterceptor(networkCheckInterceptor(isInternetAvailable))
        }.build()

fun networkCheckInterceptor(isInternetAvailable: () -> Boolean) = Interceptor { chain ->
    chain.run {
        if (!isInternetAvailable()) {
            throw NoNetworkException("Is not available network!!!")
        }
        proceed(chain.request())
    }
}

class NoNetworkException(message: String) : Throwable(message)