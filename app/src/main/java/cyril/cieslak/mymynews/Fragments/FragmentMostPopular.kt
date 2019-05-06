package cyril.cieslak.mymynews.Fragments


import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cyril.cieslak.mymynews.ItemNewsAdapter


import cyril.cieslak.mymynews.R
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


@Suppress("UNREACHABLE_CODE")
class FragmentMostPopular : Fragment(), SwipeRefreshLayout.OnRefreshListener {


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

    val jsonTopStories = "https://api.nytimes.com/svc/topstories/v2/home.json?api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"


    lateinit var swipeRefreshLayout : SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var theview = inflater.inflate(R.layout.fragment_fragment_most_popular, container, false)
        swipeRefreshLayout = theview.findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener(this)

        // Inflate the layout for this fragment
        return theview


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var jsonDataPreview = JSONDownloaderTopStories(context!!, jsonTopStories).execute().get()


        //    readJSonTwo
        datas = parseDatasFromFake(jsonDataPreview)


        adapter = ItemNewsAdapter(datas)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_in_layout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    private fun parseDatasFromFake(jsonDataPreview : String): MutableList<MutableList<String>> {

        val inputStream: InputStream = context!!.assets!!.open("dataFake.json")
        var json = inputStream.bufferedReader().use { it.readText() }

  //      json = jsonDataPreview


        try {

            var jo: JSONObject
            datas.clear()
            var data: TopStoryData

            jo = JSONObject(json)

            val ja = jo.getJSONArray("results")


            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)


                val title = jo.getString("title")
                val section = jo.getString("section")
                val subsection = jo.getString("subsection")
                val updated_date = jo.getString("updated_date")

                //***--- PREPARATION OF THE SUBSECTION TO PRINT with a " > " before the texte to print---***//
                var subsectionReadyToPrint : String
                when (subsection) {
                    "" ->  subsectionReadyToPrint = subsection
                    else -> subsectionReadyToPrint = " > $subsection" }
                //***--------------------------------***//

                //***--- FORMATTING THE DATE ---***//
                var date10char = updated_date.take(10)
                var date7char = updated_date.take(7)
                var dateYear = date10char.take(4)
                var dateMonth = date7char.takeLast(2)
                var dateDay = date10char.takeLast(2)
                var dateToPrint = "$dateDay/$dateMonth/$dateYear"
                //***--------------------------------***//


                val jam = jo.getJSONArray("multimedia")
                var jom = jam[0] as JSONObject
                var url = jom.getString("url")

                //***--- GET AN IMAGE EVEN WHEN MULTIMEDIA IS EMPTY  ---**//
                var urlToPrint : String
                when (url) {
                    "" -> urlToPrint = "https://i5.photobucket.com/albums/y152/courtney210/wave-bashful_zps5ab77563.jpg"
                    else -> urlToPrint = url }
             //   [URL=https://s5.photobucket.com/user/courtney210/media/wave-bashful_zps5ab77563.jpg.html][IMG]https://i5.photobucket.com/albums/y152/courtney210/wave-bashful_zps5ab77563.jpg[/IMG][/URL]
                //***--------------------------------***//

                val data = mutableListOf<String>(section, subsectionReadyToPrint, title, dateToPrint, urlToPrint)
                datas.add(data)



            }

            return datas

        } catch (e: JSONException) {
            e.printStackTrace()

        }
        return datas
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
    class JSONDownloaderTopStories(private var c: Context, private var jsonTopStories: String) :
        AsyncTask<Void, Void, String>() {


        private lateinit var pd: ProgressDialog
        lateinit var bingo: String


        // Connect to NetWork via HTTPURLConnection
        // ***

        // ***
        private fun connect(jsonTopStories: String): Any {

            try {
                val url = URL(jsonTopStories)
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

            val connection = connect(jsonTopStories)
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
                Toast.makeText(
                    c,
                    "Network Connection and Download Succesfull. Now Attempting to Parse",
                    Toast.LENGTH_LONG
                ).show()
                bingo = jsonData
                Toast.makeText(c, "$bingo", Toast.LENGTH_LONG).show()

                // JSONParser(c, jsonData, myGridView).execute()
            }


        }


    }
    override fun onRefresh() {
        Toast.makeText(context, "OnRefresh Pulled", Toast.LENGTH_SHORT).show()
        JSONDownloaderTopStories(context!!, jsonTopStories).execute()
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false)
        }
    }
}







