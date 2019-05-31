package cyril.cieslak.mymynews

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cyril.cieslak.mymynews.Utils.CalendarFragment
import kotlinx.android.synthetic.main.fragment_check_box.*
import kotlinx.android.synthetic.main.fragment_search_button.*
import kotlin.properties.Delegates

class SearchActivity : AppCompatActivity(), CalendarFragment.CalendarFragmentListener {

    companion object {
        val TERMS_FOR_RESEARCH_API = "TermsForResearchApi"
        val TEXT_SIZE_ZERO = 0
        val TEXT_SIZE_ONE = 1
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

        var checkedArt: String
        var checkedPolitics: String
        var checkedBusiness: String
        var checkedSport: String
        var checkedEntrepreneurs: String
        var checkedTravel: String


        // Test Edit Text has value and At least one Checkbox is checked
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
                if (editTextForSearch.length() > TEXT_SIZE_ONE) {
                    editTextSize = editTextForSearch.length()
                    Toast.makeText(baseContext, "le nombre de caractères  est de $editTextSize", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })

        ///// --- CHECK BOX Tested Empty or Not --- ///////

        var artsIsCheckedOrNot = false
        var businessIsCheckedOrNot = false
        var entrepreneursIsCheckedOrNot = false
        var politicsIsCheckedOrNot = false
        var sportsIsCheckedOrNot = false
        var travelIsCheckedOrNot = false

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
                button_search.isEnabled = true
                button_search.setBackgroundColor(Color.BLUE)
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
                button_search.isEnabled = true
                button_search.setBackgroundColor(Color.BLUE)

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
                button_search.isEnabled = true
                button_search.setBackgroundColor(Color.BLUE)

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
                button_search.isEnabled = true
                button_search.setBackgroundColor(Color.BLUE)

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
                button_search.isEnabled = true
                button_search.setBackgroundColor(Color.BLUE)

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
                button_search.isEnabled = true
                button_search.setBackgroundColor(Color.BLUE)

            }
        }

        var theCheckBoxStringTrueFalse =
            "$artsIsCheckedOrNot $businessIsCheckedOrNot $entrepreneursIsCheckedOrNot $politicsIsCheckedOrNot $sportsIsCheckedOrNot $travelIsCheckedOrNot".trim()
        var validCheckBoxString = theCheckBoxStringTrueFalse.contains("true", ignoreCase = false)


        // VIEW MODEL TO COLLECT THE DATES FROM THE FRAGMENT
        // ShareViewModel entering the scope of SearchActivity
        val sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)

        var entryDateAfterViewModel: String by Delegates.observable("11111111") { property, oldValue, newValue ->
            Toast.makeText(
                this,
                " the old value was : $oldValue and the new one is ENTRYDATEAFTERVIEWMODEL : $newValue",
                Toast.LENGTH_SHORT
            )
                .show()
        }

        var endDateAfterViewModel: String by Delegates.observable("22222222") { property, oldValue, newValue ->
            Toast.makeText(
                this,
                " the old value was : $oldValue and the new one is ENDDATEATERVIEWMODEL : $newValue",
                Toast.LENGTH_SHORT
            )
                .show()
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



            Log.i(
                "test",
                " le texte ecrit est :$editTextSize et les valeur sont arts :  $artsIsCheckedOrNot, $businessIsCheckedOrNot, $entrepreneursIsCheckedOrNot, $politicsIsCheckedOrNot, $sportsIsCheckedOrNot, $travelIsCheckedOrNot "
            )
            val querytext = editTextForSearch.text


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

            Toast.makeText(this, "Search Button Clicked", Toast.LENGTH_SHORT).show()


            var stringWithTermsForRequest =
                ("$checkedArt$checkedPolitics$checkedBusiness$checkedSport$checkedEntrepreneurs$checkedTravel").trim()
            var termsForResearchApi = "$stringWithTermsForRequest".replace("\\s".toRegex(), "")


            val stringForRequest =
                "https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=${entryDateAfterViewModel}&end_date=$endDateAfterViewModel&q=$querytext&fq=news_desk($termsForResearchApi)&sort=relevance&api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"
            Log.i("Textes", "$stringForRequest")

            var intent = Intent(button.context, ResultSearchActivity::class.java)
            // The string for the APi request to the server:
            intent.putExtra(TERMS_FOR_RESEARCH_API, "$stringForRequest")

            button_search.context.startActivity(intent)


        })


    }

}


