@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.util

import android.view.View
import android.widget.TextView

inline fun TextView.setTextAutoVisibility(message: String?) {
    text = message
    show { !message.isNullOrEmpty() }
}

inline fun View.show(body: () -> Boolean) {
    visibility = View.VISIBLE.takeIf { body() } ?: View.GONE
}