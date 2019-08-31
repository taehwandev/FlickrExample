package tech.thdev

import io.reactivex.subjects.BehaviorSubject

object TestEvent {

    val testSubject = BehaviorSubject.create<Boolean>()

    fun sendSuccess() {
        testSubject.onNext(true)
    }

    fun sendFail() {
        testSubject.onNext(false)
    }
}
