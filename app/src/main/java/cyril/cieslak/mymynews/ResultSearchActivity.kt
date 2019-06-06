package cyril.cieslak.mymynews

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.RoomOpenHelper
import android.content.ContentValues
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.transition.Slide
import android.transition.TransitionManager
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import cyril.cieslak.mymynews.Parsers.parseDatas
import cyril.cieslak.mymynews.Parsers.parseDatasResultSearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_result_search.*
import kotlinx.android.synthetic.main.popup_layout.*
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

class ResultSearchActivity : AppCompatActivity() {


    var datas = mutableListOf(
        mutableListOf<String>(
            "un",
            "deux",
            "trois",
            "quatre",
            "cinq",
            "six",
            "sept",
            "huit",
            "neuf",
            "dix",
            "onze",
            "douze",
            "treize",
            "quatorze",
            "quinze"
        )
    )

      var adapter = ItemNewsAdapter(datas)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search)

        //--- ToolBar ---//
        val toolbar = findViewById<Toolbar>(R.id.toolbar_result_search_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        // ShareViewModel entering the scope of ResultSearchActivity
  //      val sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        // Observable
//        var datasAfterViewModel: MutableList<MutableList<String>> by Delegates.observable(datas) { property, oldValue, newValue ->
//            datasAfterViewModelnewValue(newValue)
//        }

//        var datasAfterViewModelAndNewValues = datasAfterViewModelnewValue(newValues = datasAfterViewModel)
//
//        sharedViewModel.datasForResultSearchActivity.observe(this, Observer {
//            it?.let {
//                var datasResult = it
//                datasAfterViewModel = datasResult
//
//            }

  //      })


        //--- Retrieving the String from Search Activity ---//
//        var datas : MutableList<MutableList<String>> = intent.getStringExtra(SearchActivity.TERMS_FOR_RESEARCH_API)
//        Toast.makeText(this, termsForResearchApiFromSearchActivity, Toast.LENGTH_LONG).show()
//        Log.i("texte", "$termsForResearchApiFromSearchActivity")
        //    val jsonResultSearchActivity = termsForResearchApiFromSearchActivity

//        swipeRefreshLayout = findViewById(R.id.swiperefresh)
//        swipeRefreshLayout.setOnRefreshListener()

        // to get the String JSonData, we use the class JSONDownloaderResultSearchActivity
        //     var jsonDataPreview = JSONDownloaderResultSearchActivity(this, jsonResultSearchActivity).execute().get()


        // To Parse the result of the JSONDownloadResultSearch using the external CLass parseDatas() which include the method parseDatasFromApi
        //     datas = parseDatasResultSearchActivity().parseDatasFromApi(jsonDataPreview)


        var adapter = ItemNewsAdapter(datas)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_result_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter




    }


    class TopStoryData(
        private var m_title: String,
        private var m_section: String,
        private var m_subsection: String,
        private var m_updated_date: String
    ) {

        fun getTitle(): String {
            return m_title
        }

        fun getSection(): String {
            return m_section
        }

        fun getSubsection(): String {
            return m_subsection
        }

        fun getUpdated_Date(): String {
            return m_updated_date
        }
    }

    fun datasAfterViewModelnewValue(newValues: MutableList<MutableList<String>>) : MutableList<MutableList<String>>  {
    return newValues
    }


}
