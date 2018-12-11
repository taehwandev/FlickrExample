package tech.thdev.flickr.view.main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import tech.thdev.flickr.R
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.view.main.adapter.MainAdapter
import tech.thdev.flickr.view.main.adapter.decoration.MarginItemDecoration
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel
import tech.thdev.flickr.view.main.viewmodel.LoadDataViewModel
import tech.thdev.lifecycle.extensions.viewmodel.injectViewModel
import tech.thdev.lifecycle.extensions.viewmodel.lazyInjectViewModel
import tech.thdev.support.base.coroutines.ui.CoroutineScopeFragment

class MainFragment : CoroutineScopeFragment() {

    private val adapter: MainAdapter by lazy(LazyThreadSafetyMode.NONE) {
        MainAdapter(injectViewModel { MainAdapterViewModel() })
    }

    private val viewModel: LoadDataViewModel by lazyInjectViewModel {
        LoadDataViewModel(AllImageRepository.getInstance(FlickrApi), adapter.viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view.run {
            layoutManager = GridLayoutManager(this@MainFragment.context, 2)
            addItemDecoration(MarginItemDecoration(this@MainFragment.resources.getDimension(R.dimen.main_image_facing_margin).toInt()))
            adapter = this@MainFragment.adapter
        }

        viewModel.loadData()
        // show progress()
    }
}