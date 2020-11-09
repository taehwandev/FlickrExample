package tech.thdev.support.base.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import tech.thdev.support.base.adapter.viewmodel.BaseAdapterViewModel

@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder<BINDING : ViewDataBinding, in ITEM : Any, VIEW_MODEL : BaseAdapterViewModel>(
    layoutRes: Int,
    parent: ViewGroup,
    protected val context: Context = parent.context
) : BindingViewHolder<BINDING>(
    DataBindingUtil.bind<BINDING>(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    )!!
) {

    private lateinit var _viewModel: VIEW_MODEL

    var viewModel: VIEW_MODEL
        get() = _viewModel
        set(value) {
            _viewModel = value
        }

    fun checkItemAndBindViewHolder(item: Any?) {
        try {
            onBindViewHolder(item as? ITEM)
        } catch (e: Exception) {
            onBindViewHolder(null)
        }
    }

    /**
     * Use viewHolder
     */
    abstract fun onBindViewHolder(item: ITEM?)
}