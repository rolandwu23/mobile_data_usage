package com.grok.akm.sphtest

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.grok.akm.sphtest.View.MainActivity
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var mainActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.grok.akm.sphtest", appContext.packageName)
    }

    @Test
    fun showRecyclerView() {

        Espresso.onView(ViewMatchers.withId(R.id.activity_main_rv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}