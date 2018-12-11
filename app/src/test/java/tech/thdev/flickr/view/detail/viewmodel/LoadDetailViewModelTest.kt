package tech.thdev.flickr.view.detail.viewmodel

import org.junit.Test
import tech.thdev.flickr.data.source.detail.DetailImageInfoRepository
import tech.thdev.flickr.network.FlickrApi

class LoadDetailViewModelTest {

    private val viewModel = LoadDetailViewModel(DetailImageInfoRepository.getInstance(FlickrApi))

    @Test
    fun testLoadDetail() {
        viewModel.loadDetail("44437878270")
    }
}