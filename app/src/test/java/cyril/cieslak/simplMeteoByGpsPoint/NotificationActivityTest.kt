package cyril.cieslak.simplMeteoByGpsPoint


import org.junit.Rule
import org.junit.Test
import android.support.test.rule.ActivityTestRule
import android.widget.Switch

import junit.framework.TestCase.assertEquals

//@RunWith(AndroidJUnit4::class)
class NotificationActivityTest {

    val ENABLE = "Enable"
    val DISABLE = "Disable"

    @get:Rule
    var mActivityTestRule : ActivityTestRule<NotificationActivity> = ActivityTestRule<NotificationActivity>(NotificationActivity::class.java)

//    @Before
//    fun setUp() {
//     //   val launche = LauncherActivity(mActivityTestRule)
//    }

    @Test
    fun isTheSwitchButtonIsCheckedOrNot_Test_with_ENABLE() {

        val statusSwitchButton = ENABLE
        val switchButton = mActivityTestRule.activity.findViewById<Switch>(R.id.switch_notification_enable)

    //    val result = mActivityTestRule.activity.isTheSwitchButtonOnOrNot(statusSwitchButton)
        val resultat = mActivityTestRule.activity.isTheSwitchButtonOnOrNot(statusSwitchButton = statusSwitchButton)
        val expected =  switchButton.isChecked

        assertEquals(expected, resultat)


    }

//    @Test
//    fun isTheSwitchButtonOnOrNot_Test_with_DISABLE() {
//
//        val statusSwitchButton = DISABLE
//
//        val result = mActivityTestRule.activity.isTheSwitchButtonOnOrNot(statusSwitchButton)
//
//        assertEquals(statusSwitchButton, result)
//
//    }
}