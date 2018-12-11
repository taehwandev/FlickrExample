package tech.thdev.flickr.view.main.adapter.holder

import android.view.ViewGroup
import tech.thdev.flickr.R
import tech.thdev.flickr.data.DefaultPhoto
import tech.thdev.flickr.databinding.ItemSmallImageBinding
import tech.thdev.flickr.view.main.adapter.holder.viewmodel.ImageViewHolderViewModel
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.support.base.adapter.holder.BaseViewHolder

class ImageSmallViewHolder(parent: ViewGroup) :
        BaseViewHolder<ItemSmallImageBinding, DefaultPhoto, MainAdapterViewModel>(R.layout.item_small_image, parent) {

    override fun onBindViewHolder(item: DefaultPhoto?) {
        binding.viewModel = ImageViewHolderViewModel(item, adapterPosition, viewModel)
    }
}