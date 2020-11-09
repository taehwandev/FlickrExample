package tech.thdev.flickr.view.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.thdev.flickr.TestEvent
import tech.thdev.flickr.data.source.detail.DetailImageInfoRepository
import tech.thdev.flickr.util.addComma

class LoadDetailViewModel(
    private val loadDetailRepository: DetailImageInfoRepository
) : ViewModel() {

    lateinit var showErrorMessage: (message: String) -> Unit
    lateinit var setPhotoInfo: (title: String, tvDescription: String, ownerName: String, date: String, viewCount: String, commentCount: String) -> Unit
    lateinit var loadPhoto: (url: String, imageUrlLarge: String) -> Unit

    fun loadDetail(photoId: String) = viewModelScope.launch {
        loadDetailRepository.loadDetail(photoId, onError = {
            showErrorMessage(it.message ?: "")
            TestEvent.loadFail()

        }) { photoItem ->
            photoItem.photo.let { photo ->
                loadPhoto(photo.imageUrl, photo.imageUrlLarge)
            }

            setPhotoInfo(
                photoItem.photo.title.content,
                photoItem.photo.description.content,
                photoItem.photo.owner.realName.takeIf { name -> !name.isEmpty() }
                    ?: photoItem.photo.owner.username,
                photoItem.photo.dates.taken,
                photoItem.photo.views.addComma(),
                photoItem.photo.comments.content.addComma())

            TestEvent.loadSuccess()
        }
    }
}