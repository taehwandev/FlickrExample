package tech.thdev.flickr.view.detail.viewmodel

import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.data.source.detail.DetailImageInfoRepository
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.network.util.createRetrofit

class LoadDetailViewModelTest {

    private lateinit var viewModel: LoadDetailViewModel

    /**
     * 함수에 대한 호출이 잘 일어나는지 확인한다.
     */
    @Test
    fun testLoadDetail() = runBlocking {
        viewModel = LoadDetailViewModel(
            DetailImageInfoRepository.getInstance(
                createRetrofit(
                    FlickrApi::class.java,
                    FLICKR_DEFAULT_ADDRESS
                ) {
                    true
                })
        )

        viewModel.run {
            var isEnd = false
            loadPhoto = { url, imageUrlLarge ->
                assert(url.isNotEmpty())
                assert(imageUrlLarge.isNotEmpty())
            }

            setPhotoInfo = { title, _, _, _, _, _ ->
                assert(title.isNotEmpty())
                isEnd = true
            }

            loadDetail("44437878270")

            launch {
                while (isActive) {
                    if (isEnd) {
                        break
                    }
                }
            }.join()

            // 대기 후 종료
            assert(isEnd)
        }
    }

    /**
     * 함수에 대한 호출이 잘 일어나는지 확인하며, onError가 잘 동작하는지 체크한다.
     */
    @Test
    fun testLoadDetailFailApiKey() = runBlocking {
        viewModel = LoadDetailViewModel(
            DetailImageInfoRepository.getInstance(
                createRetrofit(
                    FlickrApi::class.java,
                    FLICKR_DEFAULT_ADDRESS
                ) {
                    true
                }, "apikey error"
            )
        )

        viewModel.run {
            var isEnd = false
            showErrorMessage = {
                assert(it.isNotEmpty())
                isEnd = true
            }

            loadDetail("44437878270")

            launch {
                while (isActive) {
                    if (isEnd) {
                        break
                    }
                }
            }.join()

            // 대기 후 종료
            assert(isEnd)
        }
    }

    /**
     * 함수에 대한 호출이 잘 일어나는지 확인하며, onError가 잘 동작하는지 체크한다.
     */
    @Test
    fun testLoadDetailFailNotFoundId() = runBlocking {
        viewModel = LoadDetailViewModel(
            DetailImageInfoRepository.getInstance(
                createRetrofit(
                    FlickrApi::class.java,
                    FLICKR_DEFAULT_ADDRESS
                ) {
                    true
                })
        )

        viewModel.run {
            var isEnd = false
            showErrorMessage = {
                assert(it.isNotEmpty())
                assert(it == "Photo not found")
                isEnd = true
            }

            loadDetail("1920123123")

            launch {
                while (isActive) {
                    if (isEnd) {
                        break
                    }
                }
            }.join()

            // 대기 후 종료
            assert(isEnd)
        }
    }
}