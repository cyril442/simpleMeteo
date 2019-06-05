package cyril.cieslak.mymynews.UtilsClass

import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_check_box.*

class termsForResearchApi {

    fun query(editTextForSearch: EditText): String {

        val querytext = editTextForSearch.text.toString()
        return querytext
    }


    fun termsForR(
        checkBoxArts: CheckBox,
        checkBoxBusiness: CheckBox,
        checkBoxEntrepreneurs: CheckBox,
        checkBoxPolitics: CheckBox,
        checkBoxSport: CheckBox,
        checkBoxTravel: CheckBox
    ): String {

        var checkedArt: String
        var checkedPolitics: String
        var checkedBusiness: String
        var checkedSport: String
        var checkedEntrepreneurs: String
        var checkedTravel: String


        var artsChecked: Boolean = checkBoxArts.isChecked
        if (artsChecked == true) {
            checkedArt = "\"Arts\""
        } else {
            checkedArt = ""
        }

        val politicsChecked: Boolean = checkBoxPolitics.isChecked
        if (politicsChecked == true) {
            checkedPolitics = "\"Politics\""
        } else {
            checkedPolitics = ""
        }

        val businessChecked: Boolean = checkBoxBusiness.isChecked
        if (businessChecked == true) {
            checkedBusiness = "\"Business\""
        } else {
            checkedBusiness = ""
        }

        val sportChecked: Boolean = checkBoxSport.isChecked
        if (sportChecked == true) {
            checkedSport = "\"Sports\""
        } else {
            checkedSport = ""
        }

        val entrepreneursChecked: Boolean = checkBoxEntrepreneurs.isChecked
        if (entrepreneursChecked == true) {
            checkedEntrepreneurs = "\"Entrepreneurs\""
        } else {
            checkedEntrepreneurs = ""
        }

        val travelChecked: Boolean = checkBoxTravel.isChecked
        if (travelChecked == true) {
            checkedTravel = "\"Travel\""
        } else {
            checkedTravel = ""
        }

        //   Toast.makeText(this, "Search Button Clicked", Toast.LENGTH_SHORT).show()


        var stringWithTermsForRequest =
            ("$checkedArt$checkedPolitics$checkedBusiness$checkedSport$checkedEntrepreneurs$checkedTravel").trim()
        var termsForResearchApi = "$stringWithTermsForRequest".replace("\\s".toRegex(), "")

        return termsForResearchApi
    }
}