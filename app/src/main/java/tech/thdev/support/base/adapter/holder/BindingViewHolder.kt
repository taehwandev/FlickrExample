package tech.thdev.support.base.adapter.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BindingViewHolder<T : ViewDataBinding>(protected val binding: T) : RecyclerView.ViewHolder(binding.root)