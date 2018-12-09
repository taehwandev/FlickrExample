package tech.thdev.support.base.adapter.viewmodel

import org.junit.Before
import org.junit.Test
import tech.thdev.support.base.adapter.util.cast

class BaseAdapterViewModelTest {

    private lateinit var adapterViewModel: BaseAdapterViewModel

    @Before
    fun setUp() {
        adapterViewModel = object : BaseAdapterViewModel() {
            // Do nothing
            init {
                adapterRepository.addItem(0, true)
                adapterRepository.addItem(0, "item")
                adapterRepository.addItem(0, 0.0)
            }
        }
    }

    /**
     * item의 cast를 테스트한다.
     *
     * kotlin에서 any to generic 타입으로 cast 할 경우 실제 코드는 아래와 같다.
     *
     * ex) item as ITEM(Generic 타입)
     *  > 원하는 코드 return (ITEM) item
     *
     * 하지만 컨버팅된 코드에서는 (ITEM)이 없는 형태로 Object가 리턴된다.
     * > return item : (Object)
     *
     * 그래서 코틀린에서는 [https://kotlinlang.org/docs/reference/typecasts.html#unchecked-casts] reified를 통해 cast 할 수 있어 이에 대한 테스트를 추가하였다.
     *
     * 실제 코드에서는 보내고/받는쪽 모두 generic 정의로 인해 try/catch를 사용하였다.
     */
    @Test
    fun itemCastTest() {
        // java.lang.ClassCastException: java.lang.Boolean cannot be cast to java.lang.String
//        assert(adapterViewModel.adapterRepository.getItem(0) as String != null)

        // Success
        assert(adapterViewModel.adapterRepository.getItem(0) as Boolean)

        // inline fun <reified T: Any> Any?.cast(): T? = this as? T
        assert(adapterViewModel.adapterRepository.getItem(0).cast<Boolean>() == true)
    }
}