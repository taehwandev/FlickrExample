package tech.thdev.flickr.view.main.adapter.holder.viewmodel

import androidx.databinding.ObservableField
import tech.thdev.flickr.data.DefaultPhoto
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel

class ImageViewHolderViewModel(private val item: DefaultPhoto?,
                               private val adapterPosition: Int,
                               private val adapterViewModel: MainAdapterViewModel) {

    val title: ObservableField<String>
        get() = ObservableField(item?.title ?: "")

    val imageUrl: ObservableField<String>
        get() = ObservableField(item?.imageUrl ?: "")

    fun onClickItem() {
        adapterViewModel.onClickItem(adapterPosition)
    }
}