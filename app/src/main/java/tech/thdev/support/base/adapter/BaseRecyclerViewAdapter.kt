package tech.thdev.support.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import tech.thdev.support.base.adapter.data.source.AdapterRepository
import tech.thdev.support.base.adapter.holder.BaseViewHolder
import tech.thdev.support.base.adapter.viewmodel.BaseAdapterViewModel

abstract class BaseRecyclerViewAdapter<VIEW_MODEL : BaseAdapterViewModel>(
        val viewModel: VIEW_MODEL) : RecyclerView.Adapter<BaseViewHolder<*, *, VIEW_MODEL>>() {

    // Adapter data.
    private val adapterRepository: AdapterRepository by lazy(LazyThreadSafetyMode.NONE) {
        AdapterRepository()
    }

    init {
        viewModel.run {
            adapterRepository = this@BaseRecyclerViewAdapter.adapterRepository
            notifyDataSetChanged = this@BaseRecyclerViewAdapter::notifyDataSetChanged
            notifyItemChanged = this@BaseRecyclerViewAdapter::notifyItemChanged
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *, VIEW_MODEL> =
            createViewHolder(viewType, parent).also { it.viewModel = viewModel }

    abstract fun createViewHolder(viewType: Int, parent: ViewGroup): BaseViewHolder<*, *, VIEW_MODEL>

    override fun getItemCount(): Int =
            adapterRepository.itemCount

    override fun getItemViewType(position: Int): Int =
            adapterRepository.getItemViewType(position)

    override fun onBindViewHolder(holder: BaseViewHolder<*, *, VIEW_MODEL>, position: Int) {
        holder.checkItemAndBindViewHolder(adapterRepository.getItem(position))
    }
}