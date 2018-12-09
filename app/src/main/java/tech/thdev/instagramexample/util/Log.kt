package tech.thdev.instagramexample.util

import android.os.Build
import android.util.Log
import tech.thdev.instagramexample.BuildConfig

fun i(body: () -> String) {
    Log.i("FlickrExample", body())
}