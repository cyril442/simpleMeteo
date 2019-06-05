package cyril.cieslak.mymynews.UtilsClass

import android.graphics.Color
import android.util.Size
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_check_box.*
import kotlinx.android.synthetic.main.fragment_search_button.*



class checkBoxEmptyOrNot () {



    ///// --- CHECK BOX Tested Empty or Not --- ///////

    var artsIsCheckedOrNot = false
    var businessIsCheckedOrNot = false
    var entrepreneursIsCheckedOrNot = false
    var politicsIsCheckedOrNot = false
    var sportsIsCheckedOrNot = false
    var travelIsCheckedOrNot = false




fun checkBoxStatus (editTextSize: Int, button_search: Button, checkBoxArts: CheckBox, checkBoxBusiness : CheckBox, checkBoxEntrepreneurs : CheckBox, checkBoxPolitics : CheckBox, checkBoxSport: CheckBox, checkBoxTravel : CheckBox) {

    checkBoxArts.setOnClickListener {
        if (!checkBoxArts.isChecked) {
            artsIsCheckedOrNot = false
        } else {
            artsIsCheckedOrNot = true
        }
 //       Toast.makeText(, "$artsIsCheckedOrNot", Toast.LENGTH_SHORT).show()
        if (editTextSize >= 1 && artsIsCheckedOrNot) {
   //         Toast.makeText(this, "Ready to Make the Button Enable because Arts is Checked", Toast.LENGTH_SHORT)
   //             .show()
            button_search.isEnabled = true
            button_search.setBackgroundColor(Color.BLUE)
        }
    }

    checkBoxBusiness.setOnClickListener{
        if (!checkBoxBusiness.isChecked) {
            businessIsCheckedOrNot = false
        } else {
            businessIsCheckedOrNot = true
        }
   //     Toast.makeText(this, "$businessIsCheckedOrNot", Toast.LENGTH_SHORT).show()
        if (editTextSize >= 1 && businessIsCheckedOrNot == true) {
 //           Toast.makeText(this, "Ready to Make the Button Enable because Business is Checked", Toast.LENGTH_SHORT)
   //             .show()
            button_search.isEnabled = true
            button_search.setBackgroundColor(Color.BLUE)

        }
    }

    checkBoxEntrepreneurs.setOnClickListener{
        if (!checkBoxEntrepreneurs.isChecked) {
            entrepreneursIsCheckedOrNot = false
        } else {
            entrepreneursIsCheckedOrNot = true
        }
   //     Toast.makeText(this, "$entrepreneursIsCheckedOrNot", Toast.LENGTH_SHORT).show()
        if (editTextSize >= 1 && entrepreneursIsCheckedOrNot == true) {
  //          Toast.makeText(
//                this,
//                "Ready to Make the Button Enable because Entrepreneurs is Checked",
//                Toast.LENGTH_SHORT
//            ).show()
            button_search.isEnabled = true
            button_search.setBackgroundColor(Color.BLUE)

        }
    }

    checkBoxPolitics.setOnClickListener{
        if (!checkBoxPolitics.isChecked) {
            politicsIsCheckedOrNot = false
        } else {
            politicsIsCheckedOrNot = true
        }
     //   Toast.makeText(this, "$politicsIsCheckedOrNot", Toast.LENGTH_SHORT).show()
        if (editTextSize >= 1 && politicsIsCheckedOrNot == true) {
//            Toast.makeText(this, "Ready to Make the Button Enable because politics is Checked", Toast.LENGTH_SHORT)
//                .show()
            button_search.isEnabled = true
            button_search.setBackgroundColor(Color.BLUE)

        }
    }

    checkBoxSport.setOnClickListener{
        if (!checkBoxSport.isChecked) {
            sportsIsCheckedOrNot = false
        } else {
            sportsIsCheckedOrNot = true
        }
     //   Toast.makeText(this, "$sportsIsCheckedOrNot", Toast.LENGTH_SHORT).show()
        if (editTextSize >= 1 && sportsIsCheckedOrNot == true) {
//            Toast.makeText(this, "Ready to Make the Button Enable because Sport is Checked", Toast.LENGTH_SHORT)
//                .show()
            button_search.isEnabled = true
            button_search.setBackgroundColor(Color.BLUE)

        }
    }

    checkBoxTravel.setOnClickListener{
        if (!checkBoxTravel.isChecked) {
            travelIsCheckedOrNot = false
        } else {
            travelIsCheckedOrNot = true
        }
   //     Toast.makeText(this, "$travelIsCheckedOrNot", Toast.LENGTH_SHORT).show()
        if (editTextSize >= 1 && travelIsCheckedOrNot == true) {
//            Toast.makeText(this, "Ready to Make the Button Enable because Travel is Checked", Toast.LENGTH_SHORT)
//                .show()
            button_search.isEnabled = true
            button_search.setBackgroundColor(Color.BLUE)

        }
    }

}
//        var theCheckBoxStringTrueFalse =
//            "$artsIsCheckedOrNot $businessIsCheckedOrNot $entrepreneursIsCheckedOrNot $politicsIsCheckedOrNot $sportsIsCheckedOrNot $travelIsCheckedOrNot".trim()
//        var validCheckBoxString = theCheckBoxStringTrueFalse.contains("true", ignoreCase = false)
}
