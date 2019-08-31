package tech.thdev.flickr.view.detail.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import tech.thdev.flickr.data.source.detail.DetailImageInfoRepository
import tech.thdev.flickr.util.addComma

class LoadDetailViewModel(private val loadDetailRepository: DetailImageInfoRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    lateinit var showErrorMessage: (message: String) -> Unit
    lateinit var setPhotoInfo: (title: String, tvDescription: String, ownerName: String, date: String, viewCount: String, commentCount: String) -> Unit
    lateinit var loadPhoto: (imageUrlLarge: String) -> Unit

    fun loadDetail(photoId: String) {
        compositeDisposable += loadDetailRepository.loadDetail(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photoItem ->
                    photoItem.photo.let { photo ->
                        loadPhoto(photo.imageUrlLarge)
                    }

                    setPhotoInfo(
                            photoItem.photo.title.content,
                            photoItem.photo.description.content,
                            photoItem.photo.owner.realName.takeIf { name -> !name.isEmpty() }
                                    ?: photoItem.photo.owner.username,
                            photoItem.photo.dates.taken,
                            photoItem.photo.views.addComma(),
                            photoItem.photo.comments.content.addComma())
                }, {
                    showErrorMessage(it.message ?: "")
                })
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.clear()
    }
}