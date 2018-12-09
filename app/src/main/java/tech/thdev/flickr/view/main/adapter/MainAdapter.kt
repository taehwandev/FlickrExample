package tech.thdev.flickr.view.main.adapter

import android.view.ViewGroup
import tech.thdev.flickr.view.main.adapter.holder.SmallViewHolder
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.support.base.adapter.BaseRecyclerViewAdapter
import tech.thdev.support.base.adapter.holder.BaseViewHolder

class MainAdapter(viewModel: MainAdapterViewModel) : BaseRecyclerViewAdapter<MainAdapterViewModel>(viewModel) {

    override fun createViewHolder(viewType: Int, parent: ViewGroup): BaseViewHolder<*, MainAdapterViewModel> =
            SmallViewHolder(parent)
}