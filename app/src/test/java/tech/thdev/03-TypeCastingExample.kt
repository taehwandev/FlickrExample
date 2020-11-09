package tech.thdev

import org.junit.Test

/**
 * 3편 샘플 코드 - Type casting 사용 시 주의할 점
 */
class TypeCastingExample {

    private fun casting(data: Any): String =
        when (data) {
            is String -> data
            else -> "Not String"
        }

    @Test
    fun typeCastingIsSample() {
        val data: Any = 0
        assert(casting(data) == "Not String")
    }

    @Test
    fun typeCastingAsSample() {
        val data: Any = 0
        assert(data as? String == "0")
    }

    @Test
    fun typeCastingASNullableSample() {
        val data: Any = 0
        val out = data as String?
        println(out)
    }

    /**
     * 이 경우는 실패
     */
    @Test
    fun typeCastingASUnsafeCast() {
        val data: Any? = 0
        val out = data as String?
        println(out)
    }
}