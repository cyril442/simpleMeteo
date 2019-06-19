package cyril.cieslak.mymynews

import android.app.LauncherActivity
import android.content.Intent
import android.support.design.internal.NavigationMenu
import android.support.design.widget.NavigationView
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.runner.AndroidJUnit4
import cyril.cieslak.mymynews.R.id.nav_topstories
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.v4.content.ContextCompat.startActivity
import cyril.cieslak.mymynews.R.id.start
import kotlinx.android.synthetic.main.activity_main.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("cyril.cieslak.mymynews", appContext.packageName)
    }
}





