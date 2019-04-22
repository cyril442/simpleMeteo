package cyril.cieslak.mymynews.Fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import cyril.cieslak.mymynews.MainActivity

import cyril.cieslak.mymynews.R
import kotlinx.android.synthetic.main.fragment_fragment_one.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class FragmentOne : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_one, container, false)
       // tv_fragment_one.text = "Le Fragment One"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        readJSon()

    }

    private fun readJSon() : String? {


        var json: String? = null
        try {
            val inputStream: InputStream = context!!.assets!!.open("dataFake.json")
            json = inputStream.bufferedReader().use { it.readText() }

            text_view.text = json


        } catch (e: IOException) {
           Toast.makeText(context, "chattons", Toast.LENGTH_SHORT).show()
        }
        return json
    }


}
