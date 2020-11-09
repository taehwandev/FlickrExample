package tech.thdev.flickr.view.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.thdev.flickr.TestEvent
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.util.d
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel

class LoadDataViewModel(
    private val allImageRepository: AllImageRepository,
    private val mainAdapterViewModel: MainAdapterViewModel
) : ViewModel() {

    lateinit var showErrorMessage: (message: String) -> Unit
    lateinit var loadSuccess: () -> Unit

    private var isLoading = false

    /**
     * 4편 샘플 코드 - Scope Function 사용 시 알아두면 좋은 것
     * 중첩 사용 시 주의할 부분을 알아 볼 수 있는 예, 3개의 중첩을 this와 parameter로 접근하고있다.
     */
    fun loadData() {
        isLoading = true
        viewModelScope.launch {
            allImageRepository.loadImage(onError = {
                showErrorMessage(it.message ?: "")
                TestEvent.loadFail()
                isLoading = false

            }) { item ->
                d { item.toString() }
                if (item.photos.photo.isEmpty()) {
                    return@loadImage
                }
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