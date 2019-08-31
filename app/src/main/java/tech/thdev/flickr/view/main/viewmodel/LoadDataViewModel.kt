package tech.thdev.flickr.view.main.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.util.d
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel

class LoadDataViewModel(private val allImageRepository: AllImageRepository,
                        private val mainAdapterViewModel: MainAdapterViewModel) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    lateinit var showErrorMessage: (message: String) -> Unit
    lateinit var loadSuccess: () -> Unit

    private var isLoading = false

    fun loadData() {
        isLoading = true
        compositeDisposable += allImageRepository.loadImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    d { item.toString() }
                    if (item.photos.photo.isEmpty()) {
                        return@subscribe
                    }
                    mainAdapterViewModel.adapterRepository.run {
                        item.photos.photo.forEach { flickrPhoto ->
                            addItem(MainAdapterViewModel.VIEW_TYPE_TOP.takeIf { itemCount == 0 }
                                    ?: MainAdapterViewModel.VIEW_TYPE_SMALL, flickrPhoto)
                        }
                    }
                    mainAdapterViewModel.notifyDataSetChanged()
                    loadSuccess()
                    isLoading = false
                }, {
                    showErrorMessage(it.message ?: "")
                    isLoading = false
                })
    }

    fun loadMore(visibleItemCount: Int, totalItemCount: Int, firstVisibleItem: Int) {
        if (!isLoading && (firstVisibleItem + visibleItemCount) >= totalItemCount - 10) {
            loadData()
        }
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}