package cyril.cieslak.mymynews.Utils


import android.app.DatePickerDialog
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import cyril.cieslak.mymynews.R
import cyril.cieslak.mymynews.SharedViewModel
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.lang.RuntimeException
import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.Delegates.observable


class CalendarFragment : Fragment() {

    private var sharedViewModel: SharedViewModel? = null

    lateinit var listener: CalendarFragmentListener


    var entryDate: String by Delegates.observable("11111111") { property, oldValue, newValue ->
//        Toast.makeText(context, " the old value was : $oldValue and the new one is : $newValue", Toast.LENGTH_SHORT)
////            .show()
}




    var endDate: String by Delegates.observable("20190404") {property, oldValue, newValue ->
  //      Toast.makeText(context, " the old value was : $oldValue and the new one is : $newValue", Toast.LENGTH_SHORT).show()
    }

 //   lateinit var endDate: String

//    fun newInstanceAfterDateChange() : String{
//
//        var endDate: String by Delegates.observable("22222222") {property, oldValue, newValue ->
//            //      Toast.makeText(context, " the old value was : $oldValue and the new one is : $newValue", Toast.LENGTH_SHORT).show()
//        }
//        return endDate
//    }





    companion object {
        fun newInstance(): CalendarFragment {
            var entryDate: String
            var endDate: String
            return CalendarFragment()
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Creation of the ViewModel into the scope
        activity?.let {
            /**
             *  create view model in activity scope
             */
            sharedViewModel = ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }

       // 1) Calendar

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        // 2) Listener on the ImageView Begin

        date_begin_picker_button.setOnClickListener {

            val datebegin =
                DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->

                    var formatedMonth: String = "" + (mMonth + 1)
                    if (mMonth < 10) {
                        formatedMonth = "0" + (mMonth + 1)
                    }
                    var formatedDay: String = "" + (mDay)
                    if (mDay < 10) {
                        formatedDay = "0" + (mDay)
                    }

                    var mMonth = formatedMonth
                    var mDay = formatedDay

                    // set to TextView
                    selected_date.setText("" + mDay + "/" + mMonth + "/" + mYear)


                    var result = "$mYear$mMonth$mDay"


                    entryDate = result
                    listener.onDateBegin(entryDate)

                    Log.i("entry", entryDate)

                    sharedViewModel?.inputEntryDate?.postValue(entryDate)

                    Toast.makeText(activity, """$mDay - ${mMonth} - $mYear""", Toast.LENGTH_SHORT).show()
                }, year, month, day)


            //show dialog
            datebegin.show()


        }


        // 2) Listener on the ImageView End
        date_end_picker_button.setOnClickListener {
            val dateEnd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->

                var formatedMonth: String = "" + (mMonth + 1)
                if (mMonth < 10) {
                    formatedMonth = "0" + (mMonth + 1)
                }

                var formatedDay: String = "" + (mDay)
                if (mDay < 10) {
                    formatedDay = "0" + (mDay)
                }


                var mMonth = formatedMonth
                var mDay = formatedDay


                // set to TextView
                selected_end_date.setText("" + mDay + "/" + (mMonth) + "/" + mYear)

                var resultEnd = "$mYear$mMonth$mDay"

                endDate = resultEnd
                listener.onDateEnd(endDate)
                Log.i("end", endDate)

                sharedViewModel?.inputEndDate?.postValue(endDate)

                Toast.makeText(activity, """$mDay - ${mMonth} - $mYear""", Toast.LENGTH_SHORT).show()
            }, year, month, day)
            //show dialog
            dateEnd.show()


        }


        super.onViewCreated(view, savedInstanceState)


    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is CalendarFragmentListener) {
            listener = context!!
        } else {
            throw RuntimeException(
                context!!.toString()
                        + " must implement CalendarFragmentListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        //To do something on Detach or not?
    }

    interface CalendarFragmentListener {
        fun onDateBegin(entryDate : String)  {

        }
        fun onDateEnd(endDate: String) {}


    }





}
