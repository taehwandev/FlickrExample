package tech.thdev.flickr.view.main.adapter.holder

import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_small_image.*
import tech.thdev.flickr.R
import tech.thdev.flickr.data.FlickrPhoto
import tech.thdev.flickr.util.loadUrl
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.support.base.adapter.holder.BaseViewHolder

class SmallViewHolder(parent: ViewGroup) :
        BaseViewHolder<FlickrPhoto, MainAdapterViewModel>(R.layout.item_small_image, parent) {

    override fun MainAdapterViewModel.onInitViewModel() {

    }

    override fun onBindViewHolder(item: FlickrPhoto?) {
        iv_thumbnail.loadUrl(item?.imageUrl, R.drawable.placeholder_image)
        tv_title.text = item?.title
    }
}