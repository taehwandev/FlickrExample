package tech.thdev.flickr.view.main.adapter

import android.view.ViewGroup
import tech.thdev.flickr.view.main.adapter.holder.ImageLargeViewHolder
import tech.thdev.flickr.view.main.adapter.holder.ImageSmallViewHolder
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel.Companion.VIEW_TYPE_TOP
import tech.thdev.support.base.adapter.BaseRecyclerViewAdapter
import tech.thdev.support.base.adapter.holder.BaseViewHolder

class MainAdapter(viewModel: MainAdapterViewModel) : BaseRecyclerViewAdapter<MainAdapterViewModel>(viewModel) {

    override fun createViewHolder(viewType: Int, parent: ViewGroup): BaseViewHolder<*, *, MainAdapterViewModel> = when (viewType) {
        VIEW_TYPE_TOP -> ImageLargeViewHolder(parent)
        else -> ImageSmallViewHolder(parent)
    }
}