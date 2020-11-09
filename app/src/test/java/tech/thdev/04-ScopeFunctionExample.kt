package tech.thdev

import org.junit.Test
import tech.thdev.flickr.data.DefaultPhotoResponse
import tech.thdev.flickr.data.DefaultPhotos

/**
 * 4편 샘플 코드 - Scope Function 사용 시 알아두면 좋은 것
 *
 * 기본적인 사용 예
 */
class ScopeFunctionExample {

    @Test
    fun testScopeFunctions() {
        // apply
        val response = ResponseData().apply {
            data = DefaultPhotoResponse(
                photos = DefaultPhotos(
                    page = 1,
                    pages = 10,
                    perpage = 50,
                    photo = mutableListOf(),
                    1
                ), "", 200, ""
            )
        }

        // run example
        val newBlock = response.run {
            println("this. message ${this.data.message}")
        }

        // let
        response.let { data ->
            println("this. message ${data.data.message}")
        }
        // Nullable 인 경우는 아래와 같이 사용
        response?.let {

        }

        // with
        val withBlock = with(response) {
            println("this. message ${this.data.message}")
        }

        // also
        var copyData: DefaultPhotoResponse
        response.also {
            copyData = it.data.copy(message = "new message")
        }
    }

    @Test
    fun test() {
        val response = ResponseData().apply {
            data = DefaultPhotoResponse(
                photos = DefaultPhotos(
                    page = 1,
                    pages = 10,
                    perpage = 50,
                    photo = mutableListOf(),
                    1
                ), "", 200, ""
            )
        }
        response.run {
            response.data.let { data ->
                data.photos.run {
                    // ...
                }
            }
        }
    }

    @Test
    fun testNullCheckOne() {
        data class Sample(val dataOne: String?, val dataTwo: String?, val dataThree: String?)

        val nullSample = Sample(null, null, null)
        nullSample.run {
            if (dataOne != null && dataTwo != null && dataThree != null) {
                // 사용하기
            }
        }
    }

    @Test
    fun testNullCheckTwo() {
        data class Sample(val dataOne: String?, val dataTwo: String?, val dataThree: String?)

        val nullSample = Sample(null, null, null)
        nullSample.run {
            // if 대신 사용하려면?
            dataOne?.let { dataOne ->
                dataTwo?.let { dataTwo ->
                    dataThree?.let { dataThree ->
                        // 여기에서 사용하기
                    }
                }
            }
        }
    }

    @Test
    fun testNullCheckThree() {
        data class Sample(val dataOne: String?, val dataTwo: String?, val dataThree: String?)

        val nullSample = Sample(null, null, null)
        nullSample.run {
            // if 대신 사용하려면?
            safeLet(dataOne, dataTwo, dataThree) { dataOne, dataTwo, dataThree ->
                // 사용하기
            }
        }
    }

    class ResponseData {
        lateinit var data: DefaultPhotoResponse
    }
}

inline fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    dataOne: T1?,
    dataTwo: T2?,
    dataThree: T3?,
    body: (dataOne: T1, dataTwo: T2, dataThree: T3) -> R
): R? =
    if (dataOne != null && dataTwo != null && dataThree != null) {
        body(dataOne, dataTwo, dataThree)
    } else {
        null
    }