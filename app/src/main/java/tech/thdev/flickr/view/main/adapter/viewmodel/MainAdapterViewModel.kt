package tech.thdev.flickr.view.main.adapter.viewmodel

import tech.thdev.flickr.data.DefaultPhoto
import tech.thdev.support.base.adapter.util.cast
import tech.thdev.support.base.adapter.viewmodel.BaseAdapterViewModel

/**
 * Adapter를 구성하기 위한 ViewModel
 * Repository를 포함하고있다.
 */
class MainAdapterViewModel : BaseAdapterViewModel() {

    companion object {
        const val VIEW_TYPE_TOP = 1000
        const val VIEW_TYPE_SMALL = 2000
    }

    lateinit var goToDetailPage: (photoId: String) -> Unit

    fun onClickItem(adapterPosition: Int) {
        if (::goToDetailPage.isInitialized) {
            adapterRepository.getItem(adapterPosition).cast<DefaultPhoto>()?.let {
                goToDetailPage(it.photoId)
            }
        }
    }
}