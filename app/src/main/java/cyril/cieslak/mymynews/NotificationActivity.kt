package cyril.cieslak.mymynews

import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import cyril.cieslak.mymynews.Parsers.parseDatasNotification
import cyril.cieslak.mymynews.UtilsClass.JSONDownloaderResultSearchActivity
import cyril.cieslak.mymynews.UtilsClass.checkBoxEmptyOrNot
import cyril.cieslak.mymynews.UtilsClass.termsForResearchApi
import kotlinx.android.synthetic.main.fragment_check_box.*
import kotlinx.android.synthetic.main.fragment_notification_enable.*
import kotlinx.android.synthetic.main.fragment_search_button.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class NotificationActivity : AppCompatActivity() {


    companion object {
        val TEXT_SIZE_ZERO = 0
        val TEXT_SIZE_ONE = 1
        val CHANNEL_ID = "channel_ID"

    }


    var editTextSize = TEXT_SIZE_ZERO


    lateinit var checkedArt: String
    lateinit var checkedPolitics: String
    lateinit var checkedBusiness: String
    lateinit var checkedSport: String
    lateinit var checkedEntrepreneurs: String
    lateinit var checkedTravel: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        // Setting of the Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_notification_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        // the FindViewById from the Fragments collected into the Notification Activity for Treatment before request HTTP to the server
        var editTextForSearch = findViewById<EditText>(R.id.edit_query)

        // Enable Switch Button to False at launch until there is text and check from the checkbox
        switch_notification_enable.isEnabled = false

        // Set the State of The checkbox when the NotificationActivity is OnCreate
        checkBoxArts.isEnabled = false
        checkBoxBusiness.isEnabled = false
        checkBoxEntrepreneurs.isEnabled = false
        checkBoxPolitics.isEnabled = false
        checkBoxSport.isEnabled = false
        checkBoxTravel.isEnabled = false

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

                // ---- Dates for the Notification Search ---- //
                val sdf = SimpleDateFormat("yyyyMMdd")
                var todayInMilliSeconds = Date().time
                var yesterdayInMilliSeconds = todayInMilliSeconds - 86400000

                val today = sdf.format(todayInMilliSeconds)
                val yesterday = sdf.format(yesterdayInMilliSeconds)

                Log.i("dateToday", " today is : $today and yesterday was $yesterday")


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


                val stringForRequest =
                    "https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=${yesterday}&end_date=$today&q=$queryText&fq=news_desk($stringTermsForResearchApi)&sort=relevance&api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"
                Log.i("Textes", "$stringForRequest")

                // Save the StringForRequest
                saveData(stringForRequest)
                val saveStringForRequest = getData()


                /// ----- Downloading the datas from the server ---- ////
                val jsonDataPreview =
                    JSONDownloaderResultSearchActivity(this, saveStringForRequest).execute().get()
                Log.i("Textes", " la string to Parse : $jsonDataPreview")


                /// ---- Parse The datas ---- ///
                var datas = parseDatasNotification().parseDatasFromApi(jsonDataPreview)
                Log.i("Textes", "voici les données récupérrées : $datas")


                /// --- Build the Notification --- ///

                fun sendNotification() {
                    //Get an instance of NotificationManager//
                    val mBuilder = NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("New York Times")
                        .setContentText("You have $datas new articles to read ")
                    // Gets an instance of the NotificationManager service//
                    val mNotificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    // When you issue multiple notifications about the same type of event,
                    // it’s best practice for your app to try to update an existing notification
                    // with this new information, rather than immediately creating a new notification.
                    // If you want to update this notification at a later date, you need to assign it an ID.
                    // You can then use this ID whenever you issue a subsequent notification.
                    // If the previous notification is still visible, the system will update this existing notification,
                    // rather than create a new one. In this example, the notification’s ID is 001//
                    NotificationManager.IMPORTANCE_DEFAULT

                    mNotificationManager.notify(1, mBuilder.build())

                }

                // Launch Notification
                sendNotification()

                Log.i("textes", "La notification est activée par le bouton switch")
            } else {


                Log.i("textes", "La notification est désactivée par le bouton switch")

            }

        }


    }


    // Saving in Shared Preferences the Last StringForRequest created //
    fun saveData(stringForRequest: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("stringForRequest", "$stringForRequest")
            apply()
        }
    }

    // Retrieving the last stringForRequest saved into Shared Preferences
    fun getData(): String {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return ""
        val strStringForRequest = sharedPref.getString("stringForRequest", "")
        val strForStringForRequest = strStringForRequest.toString()
        //  Toast.makeText(this, " Voici la string sauvée en SharedPreferences : $str_stringForRequest", Toast.LENGTH_LONG).show()
        Log.i("NotificationActivity", " Voici la string sauvée : $strStringForRequest")
        return strForStringForRequest
    }

}
