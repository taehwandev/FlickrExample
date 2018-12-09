package tech.thdev.support.base.adapter.viewmodel

import tech.thdev.support.base.adapter.data.source.AdapterRepository
import tech.thdev.support.base.coroutines.viewmodel.CoroutineScopeViewModel

abstract class BaseAdapterViewModel(val adapterRepository: AdapterRepository) : CoroutineScopeViewModel() {

    lateinit var notifyDataSetChanged: () -> Unit

    lateinit var notifyItemChanged: (position: Int) -> Unit
}