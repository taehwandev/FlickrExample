package tech.thdev.flickr.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.include_toast_error.view.*
import tech.thdev.flickr.R
import tech.thdev.flickr.contract.KEY_PHOTO_ID
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.network.RetrofitCreate
import tech.thdev.flickr.util.adapterScrollGridLayoutManagerListener
import tech.thdev.flickr.util.createErrorToast
import tech.thdev.flickr.util.isOnline
import tech.thdev.flickr.util.launchActivity
import tech.thdev.flickr.view.detail.DetailActivity
import tech.thdev.flickr.view.main.adapter.MainAdapter
import tech.thdev.flickr.view.main.adapter.decoration.MarginItemDecoration
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel.Companion.VIEW_TYPE_TOP
import tech.thdev.flickr.view.main.viewmodel.LoadDataViewModel

class MainFragment : Fragment() {

    private val adapterViewModel by viewModels<MainAdapterViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainAdapterViewModel().apply {
                    goToDetailPage = { photoId ->
                        requireContext().launchActivity<DetailActivity> {
                            putExtra(KEY_PHOTO_ID, photoId)
                        }
                    }
                } as T
            }
        }
    }

    private val adapter: MainAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MainAdapter(adapterViewModel)
    }

    private val viewModel by viewModels<LoadDataViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoadDataViewModel(AllImageRepository.getInstance(RetrofitCreate.flickrApi), adapter.viewModel).apply {
                    showErrorMessage = this@MainFragment::showErrorView

                    loadSuccess = {
                        if (group_progress.visibility == View.VISIBLE) {
                            group_progress.visibility = View.GONE
                        }
                    }
                } as T
            }
        }
    }

    private val layoutManager: GridLayoutManager by lazy(LazyThreadSafetyMode.NONE) {
        GridLayoutManager(this@MainFragment.context, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                        when (adapter.getItemViewType(position)) {
                            VIEW_TYPE_TOP -> 2
                            else -> 1
                        }
            }
        }
    }

    private fun String.showErrorToast() {
        requireContext().createErrorToast {
            LayoutInflater.from(requireContext())
                    .inflate(R.layout.include_toast_error, null).apply {
                        tv_error_toast.text = this@showErrorToast
                    }
        }.show()
    }

    private val adapterScrollListener by lazy(LazyThreadSafetyMode.NONE) {
        adapterScrollGridLayoutManagerListener(viewModel::loadMore)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.run {
            layoutManager = this@MainFragment.layoutManager
            addItemDecoration(MarginItemDecoration(this@MainFragment.resources.getDimension(R.dimen.main_image_facing_margin).toInt()))
            addOnScrollListener(adapterScrollListener)
            adapter = this@MainFragment.adapter
        }

        if (requireContext().isOnline()) {
            viewModel.loadData()
        } else {
            showErrorView(getString(R.string.msg_network_error))
        }
    }

    override fun onDestroy() {
        recycler_view?.removeOnScrollListener(adapterScrollListener)
        super.onDestroy()
    }

    private fun showErrorView(message: String) {
        message.showErrorToast()
        group_progress.visibility = View.GONE

        if (adapter.itemCount == 0) {
            recycler_view.visibility = View.GONE
            tv_user_message.run {
                visibility = View.VISIBLE
                text = message
            }
        }
    }
}