package tech.thdev.support.base.adapter.viewmodel

import android.arch.lifecycle.ViewModel
import tech.thdev.support.base.adapter.data.source.AdapterRepository

abstract class BaseAdapterViewModel : ViewModel() {

    private lateinit var _adapterRepository: AdapterRepository

    var adapterRepository: AdapterRepository
        get() {
            if (::_adapterRepository.isInitialized.not()) {
                AdapterRepository().also { repository -> _adapterRepository = repository }
            }
            return _adapterRepository
        }
        set(value) {
            _adapterRepository = value
        }

    lateinit var notifyDataSetChanged: () -> Unit

    lateinit var notifyItemChanged: (position: Int) -> Unit
}