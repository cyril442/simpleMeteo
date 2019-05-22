package cyril.cieslak.mymynews

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import cyril.cieslak.mymynews.Fragments.FragmentMostPopular
import cyril.cieslak.mymynews.Fragments.FragmentTopStories
import cyril.cieslak.mymynews.Fragments.FragmentSports
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Page Adapter
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentTopStories(), "Top Stories")
        adapter.addFragment(FragmentMostPopular(), "Most Popular")
        adapter.addFragment(FragmentSports(), "Sports")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        // 1) Set the Custom Toolbar to the MainActivity
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


    }




    // CLASS To MANAGE the PageAdapter
    class MyViewPagerAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title : String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }

    //2) Creation of the Menu associated to the toolbar when onCreate Activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
// Each case is treated
        when (item?.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "Search Button Clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_notification -> {
     //           Toast.makeText(this, "Notification Button Clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_about -> {
//                notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
               Toast.makeText(this, "About Button Clicked", Toast.LENGTH_SHORT).show()
//                laNotification()
                return true
            }
            R.id.action_help -> {
                Toast.makeText(this, "help Button Clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            // In else, we return the default value
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
