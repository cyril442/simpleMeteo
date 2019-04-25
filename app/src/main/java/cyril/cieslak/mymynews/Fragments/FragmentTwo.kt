package cyril.cieslak.mymynews.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import cyril.cieslak.mymynews.ItemNewsAdapter

import cyril.cieslak.mymynews.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import kotlinx.android.synthetic.main.fragment_fragment_two.*
import kotlinx.android.synthetic.main.item_nyt.*
import kotlinx.android.synthetic.main.item_nyt.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class FragmentTwo : Fragment() {

    var datas = mutableListOf<String>(
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

    var adapter = ItemNewsAdapter(datas)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_two, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        //    readJSonTwo
        datas = parseDatasFromFake()

      //  datas = arrayListOf("ping", "pong", "bongo")
        adapter =ItemNewsAdapter(datas)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_in_layout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    private fun parseDatasFromFake() : MutableList<String> {

        val inputStream: InputStream = context!!.assets!!.open("dataFake.json")
        var json = inputStream.bufferedReader().use { it.readText() }

        try {

        var jo : JSONObject
            datas.clear()
        var data : TopStoryData


            jo = JSONObject(json)
            val ja = jo.getJSONArray("results")

            for (i in 0 until ja.length()) {
                jo = ja.getJSONObject(i)


                val title = jo.getString("title")
                val section = jo.getString("section")
                val subsection = jo.getString("subsection")
                val updated_date = jo.getString("updated_date")

               // data = TopStoryData(title, section, subsection, updated_date )
                val data = mutableListOf<String>(section, subsection, title, updated_date)
               datas = data


            }

            return datas

        } catch (e: JSONException) {
            e.printStackTrace()

        }
        return datas
    }
}

  class TopStoryData (
    private var m_title : String,
    private var m_section : String,
    private var m_subsection : String,
    private var m_updated_date : String
) {

    fun getTitle() : String {
        return m_title
    }

    fun getSection() : String {
        return m_section
    }

    fun getSubsection() : String {
        return m_subsection
    }

    fun getUpdated_Date() : String {
        return m_updated_date
    }
}



