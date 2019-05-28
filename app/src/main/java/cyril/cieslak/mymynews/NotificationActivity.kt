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
import cyril.cieslak.mymynews.Parsers.parseDatasNotification
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

        // --- EDIT TEXT VALUE --- ///
        editTextForSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editTextSize = TEXT_SIZE_ZERO
                if (editTextForSearch.length() >= TEXT_SIZE_ONE) {
                    editTextSize = editTextForSearch.length()
                    Toast.makeText(
                        baseContext,
                        "Ce n'est pas vide! Nombre de caractères : $editTextSize",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editTextSize = TEXT_SIZE_ZERO
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextSize = TEXT_SIZE_ZERO
                if (editTextForSearch.length() >= TEXT_SIZE_ONE) {
                    editTextSize = editTextForSearch.length()
                    Toast.makeText(baseContext, "le nombre de caractères  est de $editTextSize", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

        //    Log.i("editText", " editTextSize est égal à : $editTextSize")


        ///// --- CHECK BOX Tested Empty or Not --- ///////

        var artsIsCheckedOrNot = false
        var businessIsCheckedOrNot = false
        var entrepreneursIsCheckedOrNot = false
        var politicsIsCheckedOrNot = false
        var sportsIsCheckedOrNot = false
        var travelIsCheckedOrNot = false

//        var checkedArt = ""
//        var checkedPolitics = ""
//        var checkedBusiness = ""
//        var checkedSport = ""
//        var checkedEntrepreneurs = ""
//        var checkedTravel = ""

        checkBoxArts.setOnClickListener {
            if (!checkBoxArts.isChecked) {
                artsIsCheckedOrNot = false
            } else {
                artsIsCheckedOrNot = true
            }
            Toast.makeText(this, "$artsIsCheckedOrNot", Toast.LENGTH_SHORT).show()
            if (editTextSize >= 1 && artsIsCheckedOrNot) {
                Toast.makeText(this, "Ready to Make the Button Enable because Arts is Checked", Toast.LENGTH_SHORT)
                    .show()
                switch_notification_enable.isEnabled = true
                switch_notification_enable.setBackgroundColor(Color.BLUE)
            }
        }

        checkBoxBusiness.setOnClickListener {
            if (!checkBoxBusiness.isChecked) {
                businessIsCheckedOrNot = false
            } else {
                businessIsCheckedOrNot = true
            }
            Toast.makeText(this, "$businessIsCheckedOrNot", Toast.LENGTH_SHORT).show()
            if (editTextSize >= 1 && businessIsCheckedOrNot == true) {
                Toast.makeText(this, "Ready to Make the Button Enable because Business is Checked", Toast.LENGTH_SHORT)
                    .show()
                switch_notification_enable.isEnabled = true
                switch_notification_enable.setBackgroundColor(Color.BLUE)

            }
        }

        checkBoxEntrepreneurs.setOnClickListener {
            if (!checkBoxEntrepreneurs.isChecked) {
                entrepreneursIsCheckedOrNot = false
            } else {
                entrepreneursIsCheckedOrNot = true
            }
            Toast.makeText(this, "$entrepreneursIsCheckedOrNot", Toast.LENGTH_SHORT).show()
            if (editTextSize >= 1 && entrepreneursIsCheckedOrNot == true) {
                Toast.makeText(
                    this,
                    "Ready to Make the Button Enable because Entrepreneurs is Checked",
                    Toast.LENGTH_SHORT
                ).show()
                switch_notification_enable.isEnabled = true
                switch_notification_enable.setBackgroundColor(Color.BLUE)
            }
        }

        checkBoxPolitics.setOnClickListener {
            if (!checkBoxPolitics.isChecked) {
                politicsIsCheckedOrNot = false
            } else {
                politicsIsCheckedOrNot = true
            }
            Toast.makeText(this, "$politicsIsCheckedOrNot", Toast.LENGTH_SHORT).show()
            if (editTextSize >= 1 && politicsIsCheckedOrNot == true) {
                Toast.makeText(this, "Ready to Make the Button Enable because politics is Checked", Toast.LENGTH_SHORT)
                    .show()
                switch_notification_enable.isEnabled = true
                switch_notification_enable.setBackgroundColor(Color.BLUE)

            }
        }

        checkBoxSport.setOnClickListener {
            if (!checkBoxSport.isChecked) {
                sportsIsCheckedOrNot = false
            } else {
                sportsIsCheckedOrNot = true
            }
            Toast.makeText(this, "$sportsIsCheckedOrNot", Toast.LENGTH_SHORT).show()
            if (editTextSize >= 1 && sportsIsCheckedOrNot == true) {
                Toast.makeText(this, "Ready to Make the Button Enable because Sport is Checked", Toast.LENGTH_SHORT)
                    .show()
                switch_notification_enable.isEnabled = true
                switch_notification_enable.setBackgroundColor(Color.BLUE)

            }
        }

        checkBoxTravel.setOnClickListener {
            if (!checkBoxTravel.isChecked) {
                travelIsCheckedOrNot = false
            } else {
                travelIsCheckedOrNot = true
            }
            Toast.makeText(this, "$travelIsCheckedOrNot", Toast.LENGTH_SHORT).show()
            if (editTextSize >= 1 && travelIsCheckedOrNot == true) {
                Toast.makeText(this, "Ready to Make the Button Enable because Travel is Checked", Toast.LENGTH_SHORT)
                    .show()
                switch_notification_enable.isEnabled = true
                switch_notification_enable.setBackgroundColor(Color.BLUE)

            }
        }

     //   val newMainactivity = MainActivity()

        // Notifcation actived by the switch button
        switch_notification_enable.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                // ---- Text for the Notification Search ---- //
                var editTextForSearch = findViewById<TextView>(R.id.edit_query)
                var queryText = editTextForSearch.text

                // ---- Dates for the Notification Search ---- //
                val sdf = SimpleDateFormat("yyyyMMdd")
                var todayInMilliSeconds = Date().time
                var yesterdayInMilliSeconds = todayInMilliSeconds - 86400000

                val today = sdf.format(todayInMilliSeconds)
                val yesterday = sdf.format(yesterdayInMilliSeconds)

                Log.i("dateToday", " today is : $today and yesterday was $yesterday")

                // ---- What are the CheckBoxes Checked? ---- //

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

                var stringWithTermsForRequest =
                    ("$checkedArt$checkedPolitics$checkedBusiness$checkedSport$checkedEntrepreneurs$checkedTravel").trim()
                var termsForResearchApi = "$stringWithTermsForRequest".replace("\\s".toRegex(), "")


                val stringForRequest =
                    "https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=${yesterday}&end_date=$today&q=$queryText&fq=news_desk($termsForResearchApi)&sort=relevance&api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"
                Log.i("Textes", "$stringForRequest")

                // Save the StringForRequest
                saveData(stringForRequest)
                val saveStringForRequest = getData()


                /// ----- Downloading the datas from the server ---- ////
                val jsonDataPreview =
                    ResultSearchActivity.JSONDownloaderResultSearchActivity(this, saveStringForRequest).execute().get()
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

               // newMainactivity.setTheNotificationOn()
             //   newsNotificationOn()
                Log.i("textes", "La notification est activée par le bouton switch")
            } else {
                Log.i("textes", "La notification est désactivée par le bouton switch")

            }

        }


    }

//    fun newsNotificationOn() {
//
//
//
//    }

    // Saving in Shared Preferences the Last StringForRequest created //
    fun saveData(stringForRequest: String) {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("stringForRequest", "$stringForRequest")
            commit()
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
