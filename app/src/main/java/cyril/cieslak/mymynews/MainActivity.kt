package cyril.cieslak.mymynews

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
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
}
