package tech.thdev.flickr.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import tech.thdev.flickr.R
import tech.thdev.flickr.util.loadUrl

@BindingAdapter("imageFromUrl")
fun imageFromUrl(imageView: ImageView, imageUrl: String) {
    imageView.loadUrl(imageUrl, R.drawable.placeholder_image)
}