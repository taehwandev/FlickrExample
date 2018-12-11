package tech.thdev.flickr.view.detail.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.thdev.flickr.data.source.detail.DetailImageInfoRepository
import tech.thdev.flickr.util.addComma
import tech.thdev.support.base.coroutines.viewmodel.CoroutineScopeViewModel

class LoadDetailViewModel(private val loadDetailRepository: DetailImageInfoRepository) : CoroutineScopeViewModel() {

    lateinit var showErrorMessage: (message: String) -> Unit
    lateinit var setPhotoInfo: (title: String, tvDescription: String, ownerName: String, date: String, viewCount: String, commentCount: String) -> Unit
    lateinit var loadPhoto: (url: String, imageUrlLarge: String) -> Unit

    fun loadDetail(photoId: String) {
        launch {
            loadDetailRepository.loadDetail(photoId, onError = {
                showErrorMessage("${it.requestCode}-${it.message}")

            }) { photoItem ->
                launch(Dispatchers.Main) {
                    photoItem.photo.let { photo ->
                        loadPhoto(photo.imageUrl, photo.imageUrlLarge)
                    }

                    setPhotoInfo(
                            photoItem.photo.title.content,
                            photoItem.photo.description.content,
                            photoItem.photo.owner.realName.takeIf { name -> !name.isEmpty() }
                                    ?: photoItem.photo.owner.username,
                            photoItem.photo.dateUploaded,
                            photoItem.photo.views.addComma(),
                            photoItem.photo.comments.content.addComma())
                }
            }
        }
    }
}