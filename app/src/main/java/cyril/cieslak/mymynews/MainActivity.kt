package cyril.cieslak.mymynews

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
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
import androidx.work.*
import cyril.cieslak.mymynews.Fragments.FragmentMostPopular
import cyril.cieslak.mymynews.Fragments.FragmentTopStories
import cyril.cieslak.mymynews.Fragments.FragmentSports
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    lateinit var drawer : DrawerLayout

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
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
        toolbar.getOverflowIcon()?.setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar)

        // Set the MenuDrawer icon clickable
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        var toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white))
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        var navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Manage the NavigationView with TabLayout and viewPager
        var tabLayout = findViewById<TabLayout>(R.id.tabs)
        var  pager = findViewById<ViewPager>(R.id.viewPager)




        // Set the Notification On or off
//        val work = OneTimeWorkRequestBuilder<NotificationWorker>()
//            .build()

        // Constraints
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        val work = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()




          //  Log.i ("TimeTest", " TWENTY : $TWENTY_FOUR_HOURS_IN_SECONDS, ACtual Time : $actualTimeStamp, lastSwitchOn : $lastSwitchedOnSwitchButton, donc au total =  $timePassedSinceLastSwitchOn")

//        when (timePassedSinceLastSwitchOn >= TWENTY_FOUR_HOURS_IN_MILLISECONDS && statusSwitchButton == NotificationActivity.ENABLE) {
//                         true ->  WorkManager.getInstance().enqueue(work)
//        }


        WorkManager.getInstance().enqueue(work)



//        WorkManager.getInstance().cancelAllWork()

//        when (statusSwitchButton && twentyHoursPassedOrNot()){
//            NotificationActivity.ENABLE && true->  WorkManager.getInstance().enqueue(work)
//        }



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
               // Toast.makeText(this, "Top Stories", Toast.LENGTH_SHORT).show()
                viewPager.setCurrentItem(0)
            }


            R.id.nav_mostpopular -> {
               // Toast.makeText(this, "Most Popular", Toast.LENGTH_SHORT).show()
                viewPager.setCurrentItem(1)
            }
            R.id.nav_sports -> {
               // Toast.makeText(this, "Sports", Toast.LENGTH_SHORT).show()
                viewPager.setCurrentItem(2)
            }

            R.id.nav_search -> {
               // Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_notification -> {
               // Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
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
         //       Toast.makeText(this, "Search Button Clicked", Toast.LENGTH_SHORT).show()
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
