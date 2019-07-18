package cyril.cieslak.simplMeteoByGpsPoint

import android.annotation.SuppressLint
import android.arch.persistence.room.Transaction
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import cyril.cieslak.simplMeteoByGpsPoint.Fragments.FragmentMostPopular
import cyril.cieslak.simplMeteoByGpsPoint.Fragments.FragmentTopStories
import cyril.cieslak.simplMeteoByGpsPoint.Fragments.FragmentSports
import cyril.cieslak.simplMeteoByGpsPoint.Fragments.FragmentWeather
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.JSONDownloaderMeteoNow
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest
    lateinit var locationCallback : LocationCallback

    val REQUEST_CODE = 1000


    lateinit var drawer: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Page Adapter
        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentTopStories(), "Top Stories")
        adapter.addFragment(FragmentMostPopular(), "Most Popular")
        adapter.addFragment(FragmentSports(), "Sports")
        adapter.addFragment(FragmentWeather(), "Meteo")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        // 1) Set the Custom Toolbar to the MainActivity
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.getOverflowIcon()?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar)

        // Set the MenuDrawer icon_weather white and clickable
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white))
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        var navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Manage the NavigationView with TabLayout and viewPager
        var tabLayout = findViewById<TabLayout>(R.id.tabs)
        var pager = findViewById<ViewPager>(R.id.viewPager)

        // Check Permission for GPS point
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)

        else
        {
            buildLocationRequest()
            buildLocationCallBack()

//            val currentLat = "cuutt"
//            val currentLongi = "jfklhfdlkf"




            // Create FusedProviderClient
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            // Set Event Click on Update Button
            button_update_coordinates.setOnClickListener (View.OnClickListener {

                if(ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED   )
                {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
                    return@OnClickListener
                }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())




            })
        }


    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

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

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    // CLASS To MANAGE the PageAdapter
    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
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

    private fun buildLocationCallBack() {
        locationCallback = object : LocationCallback() {

            override fun onLocationResult(p0: LocationResult?) {
                var location = p0!!.locations.get(p0!!.locations.size-1)
                yourCurrentLatitude.text = location.latitude.toString()
                yourCurrentLongitude.text = location.longitude.toString()

                val currentLat = location.latitude.toString()
                val currentLongi = location.longitude.toString()

                            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }



}




