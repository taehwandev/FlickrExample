package tech.thdev.support.base.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import tech.thdev.support.base.adapter.viewmodel.BaseAdapterViewModel

@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder<in ITEM : Any, VIEW_MODEL : BaseAdapterViewModel>(
    layoutRes: Int,
    parent: ViewGroup,
    protected val context: Context = parent.context
) : AndroidViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
) {

    private lateinit var _viewModel: VIEW_MODEL

    var viewModel: VIEW_MODEL
        get() = _viewModel
        set(value) {
            _viewModel = value
            _viewModel.onInitViewModel()
        }

    fun checkItemAndBindViewHolder(item: Any?) {
        try {
            onBindViewHolder(item as? ITEM)
        } catch (e: Exception) {
            onBindViewHolder(null)
        }
    }

    /**
     * How to use.
     *
     * viewModel.onClick.... or the others use viewMode
     */
    @Deprecated("아마도 지울것 같은가?")
    abstract fun VIEW_MODEL.onInitViewModel()

    /**
     * Use viewHolder
     */
    abstract fun onBindViewHolder(item: ITEM?)
}