package cyril.cieslak.mymynews.Utils


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cyril.cieslak.mymynews.R
import kotlinx.android.synthetic.main.fragment_search_button.*


class SearchButtonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_button, container, false)


    }


}
