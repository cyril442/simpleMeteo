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


        var checkedArt = checkArtIsCheckedOrNot(checkBoxArts)
        var checkedPolitics = checkPoliticsIsCheckedOrNot(checkBoxPolitics)
        var checkedBusiness = checkBusinessIsCheckedOrNot(checkBoxBusiness)
        var checkedSport = checkSportIsCheckedOrNot(checkBoxSport)
        var checkedEntrepreneurs = checkEntrepreneursIsCheckedOrNot(checkBoxEntrepreneurs)
        var checkedTravel = checkTravelIsCheckedOrNot(checkBoxTravel)




        var stringWithTermsForRequest =
            ("$checkedArt$checkedPolitics$checkedBusiness$checkedSport$checkedEntrepreneurs$checkedTravel").trim()
        var termsForResearchApi = "$stringWithTermsForRequest".replace("\\s".toRegex(), "")

        return termsForResearchApi
    }

    fun checkArtIsCheckedOrNot (checkBoxArts: CheckBox) : String{
        var artch: String
        when (checkBoxArts.isChecked){
            true -> artch = "\"Arts\""
            false -> artch = ""
        }
        return artch
    }

    fun checkPoliticsIsCheckedOrNot (checkBoxPolitics: CheckBox) : String{
        var politicsch: String
        when (checkBoxPolitics.isChecked){
            true -> politicsch = "\"Politics\""
            false -> politicsch = ""
        }
        return politicsch
    }

    fun checkBusinessIsCheckedOrNot (checkBoxBusiness: CheckBox) : String{
        var businessch: String
        when (checkBoxBusiness.isChecked){
            true -> businessch = "\"Business\""
            false -> businessch = ""
        }
        return businessch
    }

    fun checkSportIsCheckedOrNot (checkBoxSport: CheckBox) : String{
        var sportch: String
        when (checkBoxSport.isChecked){
            true -> sportch = "\"Sports\""
            false -> sportch = ""
        }
        return sportch
    }

    fun checkEntrepreneursIsCheckedOrNot (checkBoxEntrepreneurs: CheckBox) : String{
        var entrepreneursch: String
        when (checkBoxEntrepreneurs.isChecked){
            true -> entrepreneursch = "\"Entrepreneurs\""
            false -> entrepreneursch = ""
        }
        return entrepreneursch
    }

    fun checkTravelIsCheckedOrNot (checkBoxTravel: CheckBox) : String{
        var travekch: String
        when (checkBoxTravel.isChecked){
            true -> travekch = "\"Travel\""
            false -> travekch = ""
        }
        return travekch
    }
}