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
import cyril.cieslak.simplMeteoByGpsPoint.Parsers.parseDatasMostPopular


import cyril.cieslak.simplMeteoByGpsPoint.R
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.JSONDownloader


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


   val jsonMostPopular = "https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"



    lateinit var swipeRefreshLayout : SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var theview = inflater.inflate(R.layout.fragment_fragment_most_popular, container, false)
        swipeRefreshLayout = theview.findViewById(R.id.swiperefreshmostpopular)
        swipeRefreshLayout.setOnRefreshListener(this)

        // Inflate the layout for this fragment
        return theview


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // to get the String JSonData, we use the class JSONDownloaderMostPopular
        var jsonDataPreview = JSONDownloader(context!!, jsonMostPopular).execute().get()


        // To Parse the result of the JSONDownloadMostPopular using the external CLass parseDatas() which include the method parseDatasFromApi
        datas = parseDatasMostPopular().parseDatasFromApi(jsonDataPreview)


        adapter = ItemNewsAdapter(datas)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_in_layout_most_popular)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }


    override fun onRefresh() {
     //   Toast.makeText(context, "OnRefresh Pulled", Toast.LENGTH_SHORT).show()
        JSONDownloader(context!!, jsonMostPopular).execute()
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false)
        }
    }
}







