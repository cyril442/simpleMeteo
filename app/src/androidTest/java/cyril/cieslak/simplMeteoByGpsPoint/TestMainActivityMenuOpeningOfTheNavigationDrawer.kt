package cyril.cieslak.simplMeteoByGpsPoint

import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.Rule
import android.support.test.rule.ActivityTestRule
import android.support.test.espresso.intent.rule.IntentsTestRule


@RunWith(AndroidJUnit4::class)
class TestMainActivityMenuOpeningOfTheNavigationDrawer {

    @get:Rule
    var mActivityRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)
    var activ = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {


    }

//    @Test
//
//    fun clickOnNavigationDrawerIsOpeningNavigationDrawer () {
//        // Open Drawer to click on navigation.
//        Espresso.onView(withId(R.id.nav_view))
//         //   .check(matches((Gravity.LEFT))) // Left Drawer should be closed.
//            .perform(click()); // Open Drawer
//
//
//    }
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

//    @Test
//    fun isTheView_Nav_View_IsPresent () {
//        val activity = activ
//        val viewById = activity.activity.findViewById<NavigationView>(R.id.drawer_layout)
//        //Assert.assertThat(viewById, notNullValue())
//        assertThat(viewById, notNullValue())
//    }


//    @Test
//    fun isThatOpeningNotificationActivity() {
//
//
//        // GIVEN - On the home screen
////        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(ViewActions.pressMenuKey()
////)
//        Espresso.onView(ViewMatchers.withId(R.id.action_notification)).perform(ViewActions.click())
//
//
//        // THEN - Verify that we navigate to the add screen
//        // .check(matches(withContentDescription(containsString("Search"))))
//        // check is activity was started
//        intended(hasComponent(NotificationActivity::class.java.getName()))
//    }

}