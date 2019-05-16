package cyril.cieslak.mymynews

import android.content.Intent
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

class SearchActivity : AppCompatActivity(), CalendarFragment.CalendarFragmentListener {

    companion object {
        val TERMS_FOR_RESEARCH_API = "TermsForResearchApi"
    }


    lateinit var calendarFragment: CalendarFragment
    var editTextSize = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Setting of the Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_search_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
                editTextSize = 0
                if (editTextForSearch.length() >= 1){editTextSize = editTextForSearch.length()
                    Toast.makeText(baseContext, "Ce n'est pas vide! Nombre de caractères : $editTextSize", Toast.LENGTH_SHORT).show()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                editTextSize = 0
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextSize = 0
               if (editTextForSearch.length() > 1){editTextSize = editTextForSearch.length()
                   Toast.makeText(baseContext, "le nombre de caractères  est de $editTextSize", Toast.LENGTH_SHORT).show()
               }
            }

        })

        ///// --- CHECK BOX Tested Empty --- ///////

        var aarts : Boolean = true
        var bbusiness = "base"
        var eentrepreneurs = "base"
        var ppolitics = "base"
        var ssport = "base"
        var ttravel = "base"


       var lengthArt = checkBoxArts.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
           when (isChecked) {
               true -> aarts = true
               false -> aarts = false
           }
        }

        checkBoxBusiness.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()

            bbusiness = isChecked.toString()
        }
        checkBoxEntrepreneurs.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
            eentrepreneurs = isChecked.toString()
        }
        checkBoxPolitics.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
            ppolitics = isChecked.toString()
        }
        checkBoxSport.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
            ssport = isChecked.toString()
        }
        checkBoxTravel.setOnCheckedChangeListener { buttonView, isChecked ->
            Toast.makeText(this,isChecked.toString(),Toast.LENGTH_SHORT).show()
            ttravel = isChecked.toString()

        }

        var sizeArt = lengthArt.toString()



        var checkboxAtLeastOnIsChecked : String = " $sizeArt ${checkBoxBusiness.isChecked} ${checkBoxEntrepreneurs.isChecked} ${checkBoxPolitics.isChecked}  ${checkBoxSport.isChecked} ${checkBoxTravel.isChecked}"




        // OnClick of the button Search
        button.setOnClickListener(View.OnClickListener {
            //// //// //// Toast to test the output of the lengthArt and sizeArt ///
            ////  Toast.makeText(this,"$checkboxAtLeastOnIsChecked",Toast.LENGTH_SHORT).show()


            Log.i("test", " le texte ecrit est :$editTextSize et les valeur sont $checkboxAtLeastOnIsChecked ")
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


            var begin = CalendarFragment.newInstance().entryDate
            var end = calendarFragment.endDate




            Toast.makeText(this, "Search Button Clicked", Toast.LENGTH_SHORT).show()
            //           Log.i("Texte affiché ", "$querytext, $checkedArt, $checkedPolitics, $checkedBusiness, $checkedSport, $checkedEntrepreneurs, $checkedTravel, $begin, $end ")

            //     Log.i("BEG", " $begin ")
            var stringWithTermsForRequest =
                ("$checkedArt$checkedPolitics$checkedBusiness$checkedSport$checkedEntrepreneurs$checkedTravel").trim()
            var termsForResearchApi = "$stringWithTermsForRequest".replace("\\s".toRegex(), "")


            val stringForRequest =
                "https://api.nytimes.com/svc/search/v2/articlesearch.json?begin_date=$begin&end_date=$end&q=$querytext&fq=news_desk($termsForResearchApi)&sort=relevance&api-key=92Nbf4KeZSKhJXGm5QA3eTgNJjFW61gW"
            Log.i("Textes", "$stringForRequest")

            var intent = Intent(button.context, ResultSearchActivity::class.java)
            // The string for the APi request to the server:
            intent.putExtra(TERMS_FOR_RESEARCH_API, "$stringForRequest")

            button_search.context.startActivity(intent)


        })

    }

}

