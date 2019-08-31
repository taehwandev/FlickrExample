package tech.thdev.flickr

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiSelector
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tech.thdev.TestEvent
import tech.thdev.flickr.view.main.MainActivity

class MainActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    fun showUI(nextEvent: () -> Unit) {
        TestEvent.testSubject
                .subscribe {
                    nextEvent()
                }
    }

    fun select(message: String, body: (() -> Unit)? = null) {
        if (device.findObject(UiSelector().textStartsWith(message)).click()) {
            body?.let { showUI(it) }
        } else {
            throw Exception("Click fail $message")
        }
    }

    fun showMessage(message: String) {
        if (device.findObject(UiSelector().text(message)).exists().not()) {
            throw Exception("Not found $message")
        }
    }

    @Test
    fun test() = showUI {
        showMessage("abc tronchoy abc")
//        select("abc tronchoy abc") {
//            showMessage("tronchoy")
//        }
    }
}