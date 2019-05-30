package cyril.cieslak.mymynews

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import cyril.cieslak.mymynews.Fragments.FragmentMostPopular
import cyril.cieslak.mymynews.Fragments.FragmentTopStories
import cyril.cieslak.mymynews.Fragments.FragmentSports
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {




    lateinit var drawer : DrawerLayout

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Color of the background
     //   root_layout_main_activity.setBackgroundColor(Color.BLUE)

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

        // Set the MenuDrawer icon clickable
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        var toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        var navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Manage the NavigationView with TabLayout and viewPager
        var tabLayout = findViewById<TabLayout>(R.id.tabs)
        var  pager = findViewById<ViewPager>(R.id.viewPager)



        // Set the Notification On or off
        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
            .build()


//        val work = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
//            .build()

//        WorkManager.getInstance().cancelAllWork()
        WorkManager.getInstance().enqueue(work)

        WorkManager.getInstance().getStatusById(work.id)
            .observe(this, Observer { workStatus ->
            Log.i ("workStatus", "workstatus = $workStatus")

                if (workStatus != null && !workStatus.state.isFinished) {
                    Log.i("workStatus", "Not yet finished ")
                    }
            })

//        WorkManager.getInstance().getStatusById(work.id)
//            .observe(this, Observer { workStatus ->
//                Log.i("workManager", "workstatus = $workStatus")
//
//                if (workStatus != null && !workStatus.state.isFinished) {
//                    Log.i("workManager", "Not yet finished ")
//                }
//            })

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){

            R.id.nav_topstories -> {
                Toast.makeText(this, "Top Stories", Toast.LENGTH_SHORT).show()
                viewPager.setCurrentItem(0)
            }


            R.id.nav_mostpopular -> {
                Toast.makeText(this, "Most Popular", Toast.LENGTH_SHORT).show()
                viewPager.setCurrentItem(1)
            }
            R.id.nav_sports -> {
                Toast.makeText(this, "Sports", Toast.LENGTH_SHORT).show()
                viewPager.setCurrentItem(2)
            }

            R.id.nav_search -> {
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_notification -> {
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NotificationActivity::class.java)
                startActivity(intent)
            }

            else -> return onNavigationItemSelected(item)
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
        super.onBackPressed()}
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
               Toast.makeText(this, "About Button Clicked", Toast.LENGTH_SHORT).show()
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
