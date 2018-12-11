package tech.thdev.support.base.adapter.holder

import android.databinding.ViewDataBinding

abstract class BindingViewHolder<T : ViewDataBinding>(protected val binding: T) : AndroidViewHolder(binding.root)