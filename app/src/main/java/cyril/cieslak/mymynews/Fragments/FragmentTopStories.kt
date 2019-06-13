package cyril.cieslak.mymynews.Fragments



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
import cyril.cieslak.mymynews.Parsers.parseDatas
import cyril.cieslak.mymynews.UtilsClass.JSONDownloader
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


@Suppress("UNREACHABLE_CODE")
class FragmentTopStories : Fragment(), SwipeRefreshLayout.OnRefreshListener {


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

    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var theview = inflater.inflate(R.layout.fragment_fragment_top_stories, container, false)
        swipeRefreshLayout = theview.findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener(this)

        // Inflate the layout for this fragment
        return theview


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // to get the String JSonData, we use the class JSONDownloaderTopStories
        var jsonDataPreview = JSONDownloader(context!!, jsonTopStories).execute().get()


        // To Parse the result of the JSONDownloadTopStories using the external CLass parseDatas() which include the method parseDatasFromApi
        datas = parseDatas().parseDatasFromApi(jsonDataPreview)


        adapter = ItemNewsAdapter(datas)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_in_layout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }


    override fun onRefresh() {
     //   Toast.makeText(context, "OnRefresh Pulled", Toast.LENGTH_SHORT).show()
        JSONDownloader(context!!, jsonTopStories).execute()
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false)
        }
    }


}