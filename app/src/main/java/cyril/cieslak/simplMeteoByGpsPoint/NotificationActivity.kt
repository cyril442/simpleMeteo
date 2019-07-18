package cyril.cieslak.simplMeteoByGpsPoint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.NotificationLauncherOrNot
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.checkBoxEmptyOrNot
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.termsForResearchApi
import kotlinx.android.synthetic.main.fragment_check_box.*
import kotlinx.android.synthetic.main.fragment_notification_enable.*

class NotificationActivity : AppCompatActivity() {


    companion object {
        val TEXT_SIZE_ZERO = 0
        val TEXT_SIZE_ONE = 1
        val SWITCH_BUTTON_STATUS = "switch_button_status"
        val ENABLE = "Enable"
        val DISABLE = "Disable"
        val KEY_NAME_CHECKBOX_TERMS_FOR_NOTIFICATION_REQUEST = "checkbox_terms_For_Notification_request"
        val KEY_NAME_QUERYTEXT_FOR_NOTIFICATION_REQUEST = "queryText_For_Notification_request"
        val LAST_SWITCH_ACTION_SAVED = "lastSwitchAction"


    }


    var editTextSize = TEXT_SIZE_ZERO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Setting of the Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_notification_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        // the FindViewById from the Fragments collected into the Notification Activity for Treatment before request HTTP to the server
        var editTextForSearch = findViewById<EditText>(R.id.edit_query)



//        ////// Switch Button for On Off
//        val statusSwitchButton: String? =
//            SharedPreference(this.applicationContext).getValueString(NotificationActivity.SWITCH_BUTTON_STATUS)
//        Log.i("testWorker", " STATUS SWITCH BUTTON : $statusSwitchButton")
//
//        isTheSwitchButtonOnOrNot(statusSwitchButton)
//
//        when (statusSwitchButton) {
//            ENABLE -> {
//                switch_notification_enable.isChecked = true
//                switch_notification_enable.isEnabled = true
//            }
//            DISABLE -> switch_notification_enable.isEnabled = false
//            else -> switch_notification_enable.isEnabled = false
//        }

        // Enable Switch Button to False at launch until there is text and check from the checkbox
        // switch_notification_enable.isEnabled = false

        // Set the State of The checkbox when the NotificationActivity is OnCreate
        checkBoxArts.isEnabled = false
        checkBoxBusiness.isEnabled = false
        checkBoxEntrepreneurs.isEnabled = false
        checkBoxPolitics.isEnabled = false
        checkBoxSport.isEnabled = false
        checkBoxTravel.isEnabled = false

        switch_notification_enable.isEnabled = false

        // Test Edit Text has value and At least one Checkbox is checked
        // --- EDIT TEXT VALUE --- ///
        editTextForSearch.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable?) {

                editTextSize = editTextForSearch.length()

                when (editTextSize) {
                    TEXT_SIZE_ZERO -> {
                        switch_notification_enable.isEnabled = false
                        switch_notification_enable.setBackgroundResource(R.color.colorPrimary)

                        checkBoxArts.isEnabled = false
                        checkBoxBusiness.isEnabled = false
                        checkBoxEntrepreneurs.isEnabled = false
                        checkBoxPolitics.isEnabled = false
                        checkBoxSport.isEnabled = false
                        checkBoxTravel.isEnabled = false

                        checkBoxArts.isChecked = false
                        checkBoxBusiness.isChecked = false
                        checkBoxEntrepreneurs.isChecked = false
                        checkBoxPolitics.isChecked = false
                        checkBoxSport.isChecked = false
                        checkBoxTravel.isChecked = false

                    }
                    else -> {
                        checkBoxArts.isEnabled = true
                        checkBoxBusiness.isEnabled = true
                        checkBoxEntrepreneurs.isEnabled = true
                        checkBoxPolitics.isEnabled = true
                        checkBoxSport.isEnabled = true
                        checkBoxTravel.isEnabled = true
                    }
                }


            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }


        })



        //  add the class CHECKBOX EMPTY OR NOT /// SWITCH_NOTIFICATION_ENABLE is equal to BUTTON_SEARCH in the class "CheckBoxEmpntyOrNot"
        checkBoxEmptyOrNot().checkBoxStatus(
            switch_notification_enable, checkBoxArts, checkBoxBusiness,
            checkBoxEntrepreneurs, checkBoxPolitics, checkBoxSport, checkBoxTravel
        )

        // Notifcation actived by the switch button
        switch_notification_enable.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                // External class "termsForResearchAPi()" with 2 functions to collect the editText and the Checkbox checked
                var stringTermsForResearchApi: String = termsForResearchApi().termsForR(
                    checkBoxArts,
                    checkBoxBusiness,
                    checkBoxEntrepreneurs,
                    checkBoxPolitics,
                    checkBoxSport,
                    checkBoxTravel
                )
                var queryText: String = termsForResearchApi().query(editTextForSearch)


                // CLASS AND FUNCTION TO SAVE THE StringForRequest
                // SharedPreference(this).save("String_for_notification", "$stringForRequest")

                SharedPreference(this).save(
                    KEY_NAME_CHECKBOX_TERMS_FOR_NOTIFICATION_REQUEST,
                    "$stringTermsForResearchApi"
                )
                SharedPreference(this).save(KEY_NAME_QUERYTEXT_FOR_NOTIFICATION_REQUEST, "$queryText")

                // To Save the status of the Button Switch to enable or disable the Worker
                SharedPreference(this).save(SWITCH_BUTTON_STATUS, ENABLE)

//                // To save the exact Time when the User, validate his Notification wish by switch the switch ON
//                var lastSwitchAction = Date().time
//                Log.i("now", "$lastSwitchAction")
//
//                SharedPreference(this).saveLong(LAST_SWITCH_ACTION_SAVED, lastSwitchAction)

                Log.i("textes", "La notification est activée par le bouton switch")
                Toast.makeText(this, "Notifcation Enable", Toast.LENGTH_SHORT).show()

                //Launching of the Function Launching the Worker
                NotificationLauncherOrNot().launchWorker()


            } else {
//
                // To Save the status of the Button Switch to enable or disable the Worker
                SharedPreference(this).save(SWITCH_BUTTON_STATUS, DISABLE)

                Log.i("textes", "La notification est désactivée par le bouton switch")
                Toast.makeText(this, "Notification Disable", Toast.LENGTH_SHORT).show()

                //Stopping the Worker
                NotificationLauncherOrNot().stopWorker()
            }

        }


    }

    fun isTheSwitchButtonOnOrNot (statusSwitchButton : String?) {

        when (statusSwitchButton) {
            ENABLE -> switch_notification_enable.isChecked = true
            ENABLE -> switch_notification_enable.isEnabled = true
            DISABLE -> switch_notification_enable.isChecked = false
            else -> switch_notification_enable.isChecked = false


        }

    }

    override fun onStart() {

        ////// Switch Button for On Off
        val statusSwitchButton: String? =
            SharedPreference(this.applicationContext).getValueString(NotificationActivity.SWITCH_BUTTON_STATUS)
        Log.i("testWorker", " STATUS SWITCH BUTTON : $statusSwitchButton")

        if (statusSwitchButton == ENABLE){
        isTheSwitchButtonOnOrNot(statusSwitchButton)}

        super.onStart()
    }



}
