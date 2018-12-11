package tech.thdev.flickr.view.main.adapter.holder

import android.view.ViewGroup
import tech.thdev.flickr.R
import tech.thdev.flickr.data.DefaultPhoto
import tech.thdev.flickr.databinding.ItemLargeImageBinding
import tech.thdev.flickr.view.main.adapter.holder.viewmodel.ImageViewHolderViewModel
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.support.base.adapter.holder.BaseViewHolder

class ImageLargeViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemLargeImageBinding, DefaultPhoto, MainAdapterViewModel>(R.layout.item_large_image, parent) {

    override fun onBindViewHolder(item: DefaultPhoto?) {
        binding.viewModel = ImageViewHolderViewModel(item, adapterPosition, viewModel)
    }
}