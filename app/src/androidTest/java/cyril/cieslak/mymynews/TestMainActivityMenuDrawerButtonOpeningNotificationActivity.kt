package cyril.cieslak.mymynews

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import android.support.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.containsString
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import android.support.test.espresso.intent.rule.IntentsTestRule


@RunWith(AndroidJUnit4::class)
class TestMainActivityMenuDrawerButtonOpeningNotificationActivity {

    @get:Rule
    var mActivityRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }


//    @Test
//    fun isThatOpeningSearchActivity(){
//
//
//        // GIVEN - On the home screen
//        Espresso.onView(ViewMatchers.withId(R.id.action_search))
//
//            // WHEN - Click on the "+" button
//            .perform(ViewActions.click())
//
//        // THEN - Verify that we navigate to the add screen
//        // .check(matches(withContentDescription(containsString("Search"))))
//        // check is activity was started
//        intended(hasComponent(SearchActivity::class.java.getName()))
//    }

    @Test
    fun isThatOpeningNotificationActivity() {


        // GIVEN - On the home screen
        Espresso.onView(ViewMatchers.withId(R.id.action_notification))

            // WHEN - Click on the "+" button
            .perform(ViewActions.click())

        // THEN - Verify that we navigate to the add screen
        // .check(matches(withContentDescription(containsString("Search"))))
        // check is activity was started
        intended(hasComponent(NotificationActivity::class.java.getName()))
    }

}