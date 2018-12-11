@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.util

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView

inline fun TextView.setHtml(message: String?) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(message)
    }
    show { !message.isNullOrEmpty() }
}

inline fun TextView.setTextAutoVisibility(message: String?) {
    text = message
    show { !message.isNullOrEmpty() }
}

inline fun View.show(body: () -> Boolean) {
    visibility = View.VISIBLE.takeIf { body() } ?: View.GONE
}