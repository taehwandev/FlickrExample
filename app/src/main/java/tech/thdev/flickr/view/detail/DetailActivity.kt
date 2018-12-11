package tech.thdev.flickr.view.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.bottom_detail_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.thdev.flickr.R
import tech.thdev.flickr.contract.KEY_PHOTO_ID
import tech.thdev.flickr.data.source.detail.DetailImageInfoRepository
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.util.loadUrl
import tech.thdev.flickr.util.setTextAutoVisibility
import tech.thdev.flickr.util.show
import tech.thdev.flickr.view.detail.viewmodel.LoadDetailViewModel
import tech.thdev.lifecycle.extensions.viewmodel.lazyInjectViewModel
import tech.thdev.support.base.coroutines.ui.CoroutineScopeActivity


class DetailActivity : CoroutineScopeActivity() {

    private val loadDetailViewModel: LoadDetailViewModel by lazyInjectViewModel {
        LoadDetailViewModel(DetailImageInfoRepository.getInstance(FlickrApi)).apply {
            setPhotoInfo = { title: String, tvDescription: String, ownerName: String, date: String, viewCount: String, commentCount: String ->
                if (!isFinishing) {
                    tv_title.setTextAutoVisibility(title)
                    tv_description.setTextAutoVisibility(tvDescription)
                    tv_owner.text = getString(R.string.msg_owner_name, ownerName)
                    tv_date.text = getString(R.string.msg_post, date)
                    tv_view_count.text = viewCount
                    tv_comment_count.text = commentCount
                }
            }

            loadPhoto = { url, urlLarge ->
                if (!isFinishing) {
                    iv_thumbnail.loadUrl(url, R.drawable.placeholder_image, onResourceReady = {
                        // 고화질의 이미지를 한 번 더 부른다
                        launch(Dispatchers.Main) {
                            iv_thumbnail_large.loadUrl(urlLarge, onResourceReady = {
                                iv_thumbnail.visibility = View.GONE
                                false
                            })
                        }
                        false
                    })
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        val photoId = intent.getStringExtra(KEY_PHOTO_ID)
        if (photoId.isNullOrEmpty()) {
            show(R.string.msg_detail_error)
        }

        loadDetailViewModel.loadDetail(intent.getStringExtra(KEY_PHOTO_ID))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}