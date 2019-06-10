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
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "ten",
            "eleven",
            "twelve",
            "thirteen",
            "fourteen",
            "fifteen"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search)

        //--- ToolBar ---//
        val toolbar = findViewById<Toolbar>(R.id.toolbar_result_search_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        //--- Retrieving the String from Search Activity ---//
        var datasBeforeParsing = intent.getStringExtra("jsonDataPreviewIntent")


        // To Parse the result of the JSONDownloadResultSearch using the external CLass parseDatas() which include the method parseDatasFromApi
        datas = parseDatasResultSearchActivity().parseDatasFromApi(datasBeforeParsing)


        var adapter = ItemNewsAdapter(datas)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_result_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

}
