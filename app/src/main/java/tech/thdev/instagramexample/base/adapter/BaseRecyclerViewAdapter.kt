package tech.thdev.instagramexample.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import tech.thdev.instagramexample.base.adapter.data.source.AdapterRepository
import tech.thdev.instagramexample.base.adapter.holder.BaseViewHolder
import tech.thdev.instagramexample.base.adapter.viewmodel.BaseAdapterViewModel

abstract class BaseRecyclerViewAdapter<VIEW_MODEL : BaseAdapterViewModel>(
    createViewModel: (adapterRepository: AdapterRepository) -> VIEW_MODEL
) :
    RecyclerView.Adapter<BaseViewHolder<Any, VIEW_MODEL>>() {

    // Adapter data.
    private val adapterRepository: AdapterRepository by lazy(LazyThreadSafetyMode.NONE) {
        AdapterRepository()
    }

    val viewModel: VIEW_MODEL = createViewModel(adapterRepository)

    init {
        viewModel.run {
            notifyDataSetChanged = this@BaseRecyclerViewAdapter::notifyDataSetChanged
            notifyItemChanged = this@BaseRecyclerViewAdapter::notifyItemChanged
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any, VIEW_MODEL> =
        createViewHolder(viewType, parent).also { it.viewModel = viewModel }

    abstract fun createViewHolder(viewType: Int, parent: ViewGroup): BaseViewHolder<Any, VIEW_MODEL>

    override fun getItemCount(): Int =
        adapterRepository.itemCount

    override fun getItemViewType(position: Int): Int =
        adapterRepository.getItemViewType(position)

    override fun onBindViewHolder(holder: BaseViewHolder<Any, VIEW_MODEL>, position: Int) {
        holder.checkItemAndBindViewHolder(adapterRepository.getItem(position))
    }
}