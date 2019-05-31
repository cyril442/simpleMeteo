package cyril.cieslak.mymynews

import android.app.ProgressDialog
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


        //--- Retrieving the String from Search Activity ---//
        var termsForResearchApiFromSearchActivity = intent.getStringExtra(SearchActivity.TERMS_FOR_RESEARCH_API)
//        Toast.makeText(this, termsForResearchApiFromSearchActivity, Toast.LENGTH_LONG).show()
//        Log.i("texte", "$termsForResearchApiFromSearchActivity")
        val jsonResultSearchActivity = termsForResearchApiFromSearchActivity

//        swipeRefreshLayout = findViewById(R.id.swiperefresh)
//        swipeRefreshLayout.setOnRefreshListener()

        // to get the String JSonData, we use the class JSONDownloaderResultSearchActivity
        var jsonDataPreview = JSONDownloaderResultSearchActivity(this, jsonResultSearchActivity).execute().get()


        // To Parse the result of the JSONDownloadResultSearch using the external CLass parseDatas() which include the method parseDatasFromApi
        datas = parseDatasResultSearchActivity().parseDatasFromApi(jsonDataPreview)


        adapter = ItemNewsAdapter(datas)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_result_search)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        popup()


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


    @Suppress("DEPRECATION")
    class JSONDownloaderResultSearchActivity(private var c: Context, private var jsonResultSearchActivity: String) :
        AsyncTask<Void, Void, String>() {


        private lateinit var pd: ProgressDialog
        lateinit var bingo: String


        // Connect to NetWork via HTTPURLConnection
        // ***

        // ***
        private fun connect(jsonResultSearchActivity: String): Any {

            try {
                val url = URL(jsonResultSearchActivity)
                val con = url.openConnection() as HttpURLConnection

                // Con Props

                con.requestMethod = "GET"
                con.connectTimeout = 15000
                con.readTimeout = 15000
                con.doInput = true

                return con
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return "URL ERROR" + e.message

            } catch (e: IOException) {
                e.printStackTrace()
                return "CONNECT ERROR" + e.message

            }

        }

        // ***
        // Download JsonData
        // ***
        private fun download(): String {

            val connection = connect(jsonResultSearchActivity)
            if (connection.toString().startsWith("Error")) {
                return connection.toString()
            }
            // DOWNLOAD
            try {
                val con = connection as HttpURLConnection
                // if response is HTTP OK
                if (con.responseCode == 200) {
                    // GET INPUT FROM STREAM
                    val `is` = BufferedInputStream(con.inputStream)
                    val br = BufferedReader(InputStreamReader(`is`))

                    val jsonData = StringBuffer()
                    var line: String?

                    do {
                        line = br.readLine()

                        if (line == null) {
                            break
                        }
                        jsonData.append(line + "\n")

                    } while (true)

                    // CLOSING RESOURCES
                    br.close()
                    `is`.close()


                    // RETURN JSON
                    return jsonData.toString()


                } else {
                    return "Error" + con.responseMessage
                }
            } catch (e: IOException) {
                e.printStackTrace()
                return "Error" + e.message
            }


        }

        // SHOW DIALOG WHILE DOWNLOADING DATAS
        override fun onPreExecute() {
            super.onPreExecute()

            pd = ProgressDialog(c)
            pd.setTitle("Download Json")
            pd.setMessage("Downloading... Please wait...")
            pd.show()
        }

        // DOWNLOADING IN BACKGROUND
        override fun doInBackground(vararg Voids: Void): String {
            return download()
        }

        // When BACKGROUNDWORK is finished, Dismiss Dialog and Pass Datas to JSONParser
        override fun onPostExecute(jsonData: String) {
            super.onPostExecute(jsonData)

            pd.dismiss()
            if (jsonData.startsWith("URL ERROR")) {
                val error = jsonData
                Toast.makeText(c, error, Toast.LENGTH_LONG).show()
                Toast.makeText(
                    c,
                    "MOST PROBABLY THE APP CANNOT CONNECT DUE TO WRONG URL SINCE MALFORMEDURLEXCEPTION WAS RAISED",
                    Toast.LENGTH_LONG
                ).show()

            } else if (jsonData.startsWith("CONNECT ERROR")) {
                val error = jsonData
                Toast.makeText(c, error, Toast.LENGTH_LONG).show()
                Toast.makeText(
                    c,
                    "MOST PROBABLY THE APP CANNOT CONNECT TO ANY NETWORK SINCE IOEXCEPTION WAS RAISED",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                // PARSE
//                Toast.makeText(
//                    c,
//                    "Network Connection and Download Succesfull. Now Attempting to Parse",
//                    Toast.LENGTH_LONG
//                ).show()
                bingo = jsonData
                //   Toast.makeText(c, "$bingo", Toast.LENGTH_LONG).show()

                // JSONParser(c, jsonData, myGridView).execute()
            }


        }


    }

    fun popup() {

        // IF NO DATAS TO SHOW -> POP UP TO Say it
        if (datas.size <= 0) {
            Toast.makeText(this, "Pas de données à afficher", Toast.LENGTH_SHORT).show()

            // >POP UP TO LAUNCH
            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_layout, root_layout_main_activity)
            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }

            popupWindow.setOnDismissListener {
                popupWindow.dismiss()
            }

            TransitionManager.beginDelayedTransition(root_layout)
            popupWindow.showAtLocation(
                root_layout, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )

        }
    }


}
