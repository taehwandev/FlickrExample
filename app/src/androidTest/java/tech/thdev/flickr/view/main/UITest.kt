package tech.thdev.flickr.view.main

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Before
import org.junit.Rule
import org.junit.Test

const val FIND_KEYWORD = "Target specific."

class UITest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var device: UiDevice

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    fun 화면에_보여(
        sleepTime: Long = 500L,
        body: () -> Unit
    ) {
        Thread.sleep(sleepTime)
        body()
    }

    fun 선택(
        message: String,
        sleepTime: Long = 500L,
        body: () -> Unit
    ) {
        if (device.findObject(UiSelector().textStartsWith(message))
                .click()
        ) {
            화면에_보여(sleepTime, body)
        } else {
            throw Exception("Click fail $message")
        }
    }

    fun 잘나와(
        message: String
    ) {
        if (device.findObject(UiSelector().text(message)).exists().not()) {
            throw Exception("Not found $message")
        }
    }

    @Test
    fun test() {
        화면에_보여 {
            선택(FIND_KEYWORD, 1000) {
                잘나와(FIND_KEYWORD)
            }
        }
    }
}