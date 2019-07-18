package cyril.cieslak.simplMeteoByGpsPoint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import cyril.cieslak.simplMeteoByGpsPoint.Parsers.parseDatasResultSearchActivity

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
