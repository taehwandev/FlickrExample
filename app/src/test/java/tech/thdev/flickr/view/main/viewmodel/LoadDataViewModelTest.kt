package tech.thdev.flickr.view.main.viewmodel

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import tech.thdev.flickr.contract.FLICKR_DEFAULT_ADDRESS
import tech.thdev.flickr.data.source.all.AllImageRepository
import tech.thdev.flickr.network.FlickrApi
import tech.thdev.flickr.network.util.createRetrofit
import tech.thdev.flickr.view.main.adapter.viewmodel.MainAdapterViewModel

class LoadDataViewModelTest {

    private lateinit var viewModel: LoadDataViewModel

    /**
     * 문제 1.
     * LoadDataViewModel 에서는 Retrofit을 이용해 데이터를 불러오고 있는데, RxJava를 이용하고있다.
     * RxJava는 IO 쓰레드를 통해 데이터를 불러오고있고, 화면에 데이터를 출력하기 위해서 AndroidScheduler를 활용하고있다.
     * 데이터가 잘 불러와지고, LoadDataViewModel에 포함되어있는 loadSuccess()가 잘 불러지고있는지 확인이 필요하다.
     *
     * 다음을 완성하고, RxJava 테스트를 성공적으로 마무리 할 수 있도록 만들어라.
     *
     * 사전 준비) LoadDataViewModel의 테스트 가능하도록 코드가 작성되어있다.
     */

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun loadTest() {
        viewModel = LoadDataViewModel(AllImageRepository.getInstance(createRetrofit(FlickrApi::class.java, FLICKR_DEFAULT_ADDRESS) {
            true
        }), MainAdapterViewModel().apply {
            notifyDataSetChanged = {}
            notifyItemChanged = {}
        })

        val testSubject = TestSubscriber<Boolean>()

        viewModel.loadSuccess = {
            testSubject.onNext(true)
        }

        viewModel.loadData()
        testSubject.run {
            awaitCount(1)
            assertValue(true)
        }
    }
}