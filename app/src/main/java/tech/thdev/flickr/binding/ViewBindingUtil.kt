package tech.thdev.flickr.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import tech.thdev.flickr.R
import tech.thdev.flickr.util.loadUrl

@BindingAdapter("imageFromUrl")
fun imageFromUrl(imageView: ImageView, imageUrl: String) {
    imageView.loadUrl(imageUrl, R.drawable.placeholder_image)
}