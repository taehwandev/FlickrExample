package tech.thdev.flickr.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

inline fun ImageView.loadUrl(url: String?,
                             placeholder: Int = 0,
                             crossinline onResourceReady: (() -> Boolean) = { false },
                             crossinline onLoadFail: (() -> Boolean) = { false }) {
    var ret = Glide.with(this.context).load(url)

    if (placeholder > 0) {
        ret = ret.apply(RequestOptions.placeholderOf(placeholder))
    }
    ret.listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean =
                onLoadFail()


        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean =
                onResourceReady()
    }).into(this)
}