package cyril.cieslak.simplMeteoByGpsPoint.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cyril.cieslak.simplMeteoByGpsPoint.ItemNewsAdapter
import cyril.cieslak.simplMeteoByGpsPoint.Parsers.parseDatas


import cyril.cieslak.simplMeteoByGpsPoint.R
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.JSONDownloader


@Suppress("UNREACHABLE_CODE")
class FragmentSports : Fragment(), SwipeRefreshLayout.OnRefreshListener {


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


    val jsonSports = "https://api.nytimes.com/svc/topstories/v2/sports.json?api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"


    lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var theview = inflater.inflate(R.layout.fragment_fragment_sports, container, false)
        swipeRefreshLayout = theview.findViewById(R.id.swiperefreshsports)
        swipeRefreshLayout.setOnRefreshListener(this)

        // Inflate the layout for this fragment
        return theview

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // to get the String JSonData, we use the class JSONDownloaderSport
        var jsonDataPreview = JSONDownloader(context!!, jsonSports).execute().get()


        // To Parse the result of the JSONDownloadSport using the external CLass parseDatas() which include the method parseDatasFromApi
        datas = parseDatas().parseDatasFromApi(jsonDataPreview)


        adapter = ItemNewsAdapter(datas)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_in_layout_sports)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    override fun onRefresh() {
    //    Toast.makeText(context, "OnRefresh Pulled", Toast.LENGTH_SHORT).show()
        JSONDownloader(context!!, jsonSports).execute()
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false)
        }
    }
}







