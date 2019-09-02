package tech.thdev.flickr.view.main.viewmodel

import kotlinx.coroutines.launch
import tech.thdev.coroutines.provider.DispatchersProvider
import tech.thdev.flickr.TestEvent
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.util.d
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.support.base.coroutines.viewmodel.CoroutineScopeViewModel

class LoadDataViewModel(
    private val allImageRepository: AllImageRepository,
    private val mainAdapterViewModel: MainAdapterViewModel,
    private val defaultDispatcher: DispatchersProvider = DispatchersProvider
) : CoroutineScopeViewModel() {

    lateinit var showErrorMessage: (message: String) -> Unit
    lateinit var loadSuccess: () -> Unit

    private var isLoading = false

    fun loadData() {
        isLoading = true
        launch {
            allImageRepository.loadImage(onError = {
                launch(defaultDispatcher.main) {
                    showErrorMessage(it.message ?: "")
                    TestEvent.loadFail()
                    isLoading = false
                }

            }) { item ->
                d { item.toString() }
                if (item.photos.photo.isEmpty()) {
                    return@loadImage
                }
                launch(defaultDispatcher.main) {
                    mainAdapterViewModel.adapterRepository.run {
                        item.photos.photo.forEach { flickrPhoto ->
                            addItem(MainAdapterViewModel.VIEW_TYPE_TOP.takeIf { itemCount == 0 }
                                ?: MainAdapterViewModel.VIEW_TYPE_SMALL, flickrPhoto)
                        }
                    }
                    mainAdapterViewModel.notifyDataSetChanged()
                    loadSuccess()
                    TestEvent.loadSuccess()
                    isLoading = false
                }
            }
        }
    }

    fun loadMore(visibleItemCount: Int, totalItemCount: Int, firstVisibleItem: Int) {
        if (!isLoading && (firstVisibleItem + visibleItemCount) >= totalItemCount - 10) {
            loadData()
        }
    }

    override fun onCleared() {
        super.onCleared()

        allImageRepository.clear()
    }
}