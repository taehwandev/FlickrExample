package tech.thdev.flickr.util

import android.util.Log

const val DEFAULT_NAME = "FlickrExample"

fun i(body: () -> String) {
    Log.i(DEFAULT_NAME, body())
}

fun d(body: () -> String) {
    Log.d(DEFAULT_NAME, body())
}