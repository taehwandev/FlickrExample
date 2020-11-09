package tech.thdev.flickr.view.main

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tech.thdev.flickr.TestEvent

class UITestCoroutines {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    suspend fun 화면에보여(body: suspend () -> Unit) {
        if (TestEvent.channel.receive()) {
            body()
        } else {
            throw Exception("노출하지 못해 실패")
        }
    }

    suspend fun 선택(message: String, body: suspend () -> Unit) {
        if (device.findObject(UiSelector().textStartsWith(message)).click()) {
            화면에보여(body)
        } else {
            throw Exception("Click fail $message")
        }
    }

    fun 잘나와(message: String) {
        if (device.findObject(UiSelector().text(message)).exists().not()) {
            throw Exception("Not found $message")
        }
    }

    @Test
    fun test() = runBlocking {
        화면에보여 {
            선택(FIND_KEYWORD) {
                잘나와(FIND_KEYWORD)
            }
        }
    }
}