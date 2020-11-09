package tech.thdev.support.base.adapter.viewmodel

import androidx.lifecycle.ViewModel
import tech.thdev.support.base.adapter.data.source.AdapterRepository

abstract class BaseAdapterViewModel : ViewModel() {

    private lateinit var _adapterRepository: AdapterRepository

    var adapterRepository: AdapterRepository
        get() = _adapterRepository.takeIf { ::_adapterRepository.isInitialized } ?: let {
            AdapterRepository().also { repository -> _adapterRepository = repository }
        }
        set(value) {
            _adapterRepository = value
        }

    lateinit var notifyDataSetChanged: () -> Unit

    lateinit var notifyItemChanged: (position: Int) -> Unit
}