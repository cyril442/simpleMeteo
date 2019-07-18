package cyril.cieslak.simplMeteoByGpsPoint.Utils


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import cyril.cieslak.simplMeteoByGpsPoint.R



class QueryFragment : Fragment() {

    companion object {
        fun newInstance() = QueryFragment()
    }

    lateinit var query : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_query, container, false)

        var resultEditText = view.findViewById<EditText>(R.id.edit_query)
        return view
    }


}
