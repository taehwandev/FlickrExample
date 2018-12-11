package tech.thdev.flickr.view.main.viewmodel

import kotlinx.coroutines.launch
import tech.thdev.coroutines.provider.DispatchersProvider
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.util.d
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.support.base.coroutines.viewmodel.CoroutineScopeViewModel

class LoadDataViewModel(private val allImageRepository: AllImageRepository,
                        private val mainAdapterViewModel: MainAdapterViewModel,
                        private val defaultDispatcher: DispatchersProvider = DispatchersProvider) : CoroutineScopeViewModel() {

    fun loadData() {
        launch {
            allImageRepository.loadImage(onError = {
                // error 처리
            }) { item ->
                d { item.toString() }
                if (item.photos.photo.isEmpty()) {
                    return@loadImage
                }
                launch(defaultDispatcher.main) {
                    mainAdapterViewModel.adapterRepository.run {
                        item.photos.photo.forEachIndexed { index, flickrPhoto ->
                            addItem(MainAdapterViewModel.VIEW_TYPE_TOP.takeIf { index == 0 }
                                    ?: MainAdapterViewModel.VIEW_TYPE_SMALL, flickrPhoto)
                        }
                    }
                    mainAdapterViewModel.notifyDataSetChanged()
                }
            }
        }
    }
}