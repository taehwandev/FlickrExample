@file:Suppress("NOTHING_TO_INLINE")

package tech.thdev.flickr.util

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

/**
 * GridLayoutManager 에 대한 리스너 callback을 제공한다.
 */
inline fun adapterScrollGridLayoutManagerListener(
        crossinline onCallback: (visibleItemCount: Int, totalItemCount: Int, firstVisibleItem: Int) -> Unit) = object : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = recyclerView.adapter?.itemCount ?: 0
        val firstVisibleItem = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

        onCallback(visibleItemCount, totalItemCount, firstVisibleItem)
    }
}

inline fun Context.createErrorToast(duration: Int = Toast.LENGTH_SHORT,
                                    xOffset: Int = 0,
                                    yOffset: Int = 270, createView: () -> View) =
        Toast(this).apply {
            setGravity(Gravity.TOP, xOffset, yOffset)
            this.duration = duration
            view = createView()
        }