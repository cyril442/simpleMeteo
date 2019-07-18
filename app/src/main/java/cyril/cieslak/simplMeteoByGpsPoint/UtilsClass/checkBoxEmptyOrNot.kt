package cyril.cieslak.simplMeteoByGpsPoint.UtilsClass

import android.widget.*
import cyril.cieslak.simplMeteoByGpsPoint.R


class checkBoxEmptyOrNot() {


    ///// --- CHECK BOX Tested Empty or Not --- ///////


    fun checkBoxStatus(
        button_search: Button,
        checkBoxArts: CheckBox,
        checkBoxBusiness: CheckBox,
        checkBoxEntrepreneurs: CheckBox,
        checkBoxPolitics: CheckBox,
        checkBoxSport: CheckBox,
        checkBoxTravel: CheckBox
    ) {



        checkBoxArts.setOnClickListener {

          //  var textSize = textSizeObservableToInt

            when (checkBoxArts.isChecked) {
                true -> {
                    button_search.isEnabled = true
                    button_search.setBackgroundResource(R.color.bluelight)
                }
                false -> {
                    if (!checkBoxBusiness.isChecked && !checkBoxEntrepreneurs.isChecked && !checkBoxPolitics.isChecked && !checkBoxSport.isChecked && !checkBoxTravel.isChecked) {
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)
                    }
                }

            }
        }

        checkBoxBusiness.setOnClickListener {
         //   var textSize = textSizeObservableToInt
            when (checkBoxBusiness.isChecked) {
                true -> {
                    button_search.isEnabled = true
                    button_search.setBackgroundResource(R.color.bluelight)
                }
                false -> {
                    if (!checkBoxArts.isChecked && !checkBoxEntrepreneurs.isChecked && !checkBoxPolitics.isChecked && !checkBoxSport.isChecked && !checkBoxTravel.isChecked) {
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)
                    }
                }
            }
        }

        checkBoxEntrepreneurs.setOnClickListener {
          //  var textSize = textSizeObservableToInt
            when (checkBoxEntrepreneurs.isChecked) {
                true -> {
                    button_search.isEnabled = true
                    button_search.setBackgroundResource(R.color.bluelight)
                }
                false -> {
                    if (!checkBoxArts.isChecked && !checkBoxBusiness.isChecked && !checkBoxPolitics.isChecked && !checkBoxSport.isChecked && !checkBoxTravel.isChecked) {
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)
                    }
                }
            }
        }

        checkBoxPolitics.setOnClickListener {
         //   var textSize = textSizeObservableToInt
            when (checkBoxPolitics.isChecked) {
                true -> {
                    button_search.isEnabled = true
                    button_search.setBackgroundResource(R.color.bluelight)
                }
                false -> {
                    if (!checkBoxArts.isChecked && !checkBoxBusiness.isChecked && !checkBoxEntrepreneurs.isChecked && !checkBoxSport.isChecked && !checkBoxTravel.isChecked) {
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)
                    }
                }
            }
        }

        checkBoxSport.setOnClickListener {
         //   var textSize = textSizeObservableToInt
            when (checkBoxSport.isChecked) {
                true -> {
                    button_search.isEnabled = true
                    button_search.setBackgroundResource(R.color.bluelight)
                }
                false -> {
                    if (!checkBoxArts.isChecked && !checkBoxBusiness.isChecked && !checkBoxEntrepreneurs.isChecked && !checkBoxPolitics.isChecked && !checkBoxTravel.isChecked) {
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)
                    }
                }
            }
        }

        checkBoxTravel.setOnClickListener {
       //     var textSize = textSizeObservableToInt
            when (checkBoxTravel.isChecked) {
                true -> {
                    button_search.isEnabled = true
                    button_search.setBackgroundResource(R.color.bluelight)
                }
                false -> {
                    if (!checkBoxArts.isChecked && !checkBoxBusiness.isChecked && !checkBoxEntrepreneurs.isChecked && !checkBoxPolitics.isChecked && !checkBoxSport.isChecked) {
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)
                    }
                }
            }
        }
    }
}

