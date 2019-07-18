package cyril.cieslak.simplMeteoByGpsPoint

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import cyril.cieslak.simplMeteoByGpsPoint.Utils.CalendarFragment
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.JSONDownloader
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.checkBoxEmptyOrNot
import cyril.cieslak.simplMeteoByGpsPoint.UtilsClass.termsForResearchApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.custom_popup.view.*
import kotlinx.android.synthetic.main.fragment_check_box.*
import kotlinx.android.synthetic.main.fragment_search_button.*
import kotlin.properties.Delegates

class SearchActivity : AppCompatActivity(), CalendarFragment.CalendarFragmentListener {

    companion object {
        val TERMS_FOR_RESEARCH_API = "TermsForResearchApi"
        val TEXT_SIZE_ZERO = 0
        val TEXT_SIZE_ONE = 1
        val TEXT_MINUS_ONE = -1
        val INITIAL_ENTRY_DATE = "20120216"
        val INITIAL_END_DATE = "20160621"
    }

    lateinit var calendarFragment: CalendarFragment
    var editTextSize = TEXT_SIZE_ZERO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Setting of the Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_search_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Set the Button to enable and grey color and puff Before the User is Filling the edit and select at least one Checkbox
        button_search.isEnabled = false


        // Recuperation of the Fragment into the Search Activity
        calendarFragment = CalendarFragment()


        // the FindViewById from the Fragments collected into the Search Activity for Treatment before request HTTP to the server
        val editTextForSearch = findViewById<EditText>(R.id.edit_query)
        val button = findViewById<Button>(R.id.button_search)

        // Set the State of The checkbox when the SearchActivity is OnCreate
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
                        button_search.isEnabled = false
                        button_search.setBackgroundResource(R.color.colorPrimary)

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


        //  add the class CHECKBOX EMPTY OR NOT
        checkBoxEmptyOrNot().checkBoxStatus(
            button_search, checkBoxArts, checkBoxBusiness,
            checkBoxEntrepreneurs, checkBoxPolitics, checkBoxSport, checkBoxTravel
        )

        // VIEW MODEL TO COLLECT THE DATES FROM THE FRAGMENT
        // ShareViewModel entering the scope of SearchActivity -> ViewModel used to pass datas from Fragment To Activity
        val sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        var entryDateAfterViewModel: String by Delegates.observable(INITIAL_ENTRY_DATE) { property, oldValue, newValue ->
            //            Toast.makeText(
//                this,
//                " the old value was : $oldValue and the new one is ENTRYDATEAFTERVIEWMODEL : $newValue",
//                Toast.LENGTH_SHORT
//            )
//                .show()
        }

        var endDateAfterViewModel: String by Delegates.observable(INITIAL_END_DATE) { property, oldValue, newValue ->
            //            Toast.makeText(
//                this,
//                " the old value was : $oldValue and the new one is ENDDATEATERVIEWMODEL : $newValue",
//                Toast.LENGTH_SHORT
//            )
//                .show()
        }





        sharedViewModel.inputEntryDate.observe(this, Observer {
            it?.let {
                //  getBegin(it)
                var begin = "$it"
                Log.i("SearchActivity", "value of Begin Second : $begin")
                entryDateAfterViewModel = begin

            }
        })


        sharedViewModel.inputEndDate.observe(this, Observer {
            it?.let {
                var end = "$it"
                Log.i("SearchActivity", "value of End : $end")
                endDateAfterViewModel = end

            }
        })

        // OnClick of the button Search
        button.setOnClickListener(View.OnClickListener {

            // External class "termsForResearchAPi()" with 2 functions to collect the editText and the Checkbox checked
            var stringTermsForResearchApi: String = termsForResearchApi().termsForR(
                checkBoxArts,
                checkBoxBusiness,
                checkBoxEntrepreneurs,
                checkBoxPolitics,
                checkBoxSport,
                checkBoxTravel
            )
            var querytext: String = termsForResearchApi().query(editTextForSearch)

            val stringForRequest =
                "https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=${entryDateAfterViewModel}&end_date=$endDateAfterViewModel&q=$querytext&fq=news_desk($stringTermsForResearchApi)&sort=relevance&api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"
            Log.i("Textes", "$stringForRequest")


            var jsonDataPreview = JSONDownloader(this, stringForRequest).execute().get()

//
            var intent = Intent(button.context, ResultSearchActivity::class.java)
            intent.putExtra("jsonDataPreviewIntent", jsonDataPreview)


            // When "url" is included into the String jsonDataPreview, then, it means that there are some results to show, and the string JsonDataPreview is sent at the ResultSearch Actovity for Parsing and adapter.
            // When "url" is NOT included into the String jsonDataPreview, the popup() method os launched to notify the User and there is no opening of the ResultSearchActivity
            Log.i("SizeDatas", "Voici les datas: $jsonDataPreview")
            when (jsonDataPreview.contains("url", ignoreCase = true)) {
                false -> popup()
                true -> button_search.context.startActivity(intent)

            }

            Log.i("SearchActivity", " the datas are : $jsonDataPreview")

        })


    }


    fun popup() {

        // IF NO DATAS TO SHOW -> POP UP TO Say it

        Toast.makeText(this, "Pas de données à afficher", Toast.LENGTH_SHORT).show()

        // >POP UP TO LAUNCH
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.custom_popup, root_layout_main_activity)
        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )


        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut

        }


        TransitionManager.beginDelayedTransition(root_layout_search_activity)
        popupWindow.showAtLocation(
            root_layout_search_activity, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )


        view.thecross.setOnClickListener {
            popupWindow.dismiss()
        }


    }


}


