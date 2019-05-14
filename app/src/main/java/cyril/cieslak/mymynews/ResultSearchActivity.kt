package cyril.cieslak.mymynews

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast
import cyril.cieslak.mymynews.Parsers.parseDatas
import cyril.cieslak.mymynews.Parsers.parseDatasResultSearchActivity
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

    lateinit var swipeRefreshLayout: SwipeRefreshLayout



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

        // to get the String JSonData, we use the class JSONDownloaderTopStories
        var jsonDataPreview = JSONDownloaderResultSearchActivity(this, jsonResultSearchActivity).execute().get()


        // To Parse the result of the JSONDownloadTopStories using the external CLass parseDatas() which include the method parseDatasFromApi
        datas = parseDatasResultSearchActivity().parseDatasFromApi(jsonDataPreview)


        adapter = ItemNewsAdapter(datas)

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

//    override fun onRefresh() {
//        Toast.makeText(context, "OnRefresh Pulled", Toast.LENGTH_SHORT).show()
//        JSONDownloaderTopStories(this, jsonResultSearchActivity).execute()
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setRefreshing(false)
//        }
//    }

//}
}
