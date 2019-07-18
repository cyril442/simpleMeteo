package cyril.cieslak.simplMeteoByGpsPoint.Utils


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cyril.cieslak.simplMeteoByGpsPoint.R


class NotificationEnableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_enable, container, false)
    }


}
