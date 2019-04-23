package cyril.cieslak.mymynews.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import cyril.cieslak.mymynews.ItemNewsAdapter

import cyril.cieslak.mymynews.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import kotlinx.android.synthetic.main.fragment_fragment_two.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class FragmentTwo : Fragment() {

    var datas = arrayOf<String>("un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze", "quinze")
    val adapter = ItemNewsAdapter(datas)

    var arr = arrayListOf<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_two, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       readJSonTwo()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_in_layout)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    private fun readJSonTwo() {

        var json: String? = null

        try {
            val inputStream: InputStream = context!!.assets!!.open("dataFake.json")
            json = inputStream.bufferedReader().use { it.readText() }

            var jsonarr = JSONArray(json)


            for (i in 0 until  jsonarr.length()-1) {

                var jsonobj = jsonarr.getJSONObject(i)
                //arr.add(jsonobj.getString("status"))
                arr.add(jsonobj.getString("section"))




            }




            var adpt = ArrayAdapter(this, R.layout.item_nyt, arr)
            recycler_view_in_layout.adapter = adpt



        } catch (e: IOException) {
            Toast.makeText(context, "Exception try", Toast.LENGTH_SHORT).show()
        }

    }
    }


