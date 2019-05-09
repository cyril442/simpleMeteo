package cyril.cieslak.mymynews

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import cyril.cieslak.mymynews.Utils.CalendarFragment
import cyril.cieslak.mymynews.Utils.QueryFragment
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_check_box.*
import kotlinx.android.synthetic.main.fragment_search_button.*

class SearchActivity : AppCompatActivity(), CalendarFragment.CalendarFragmentListener  {

    companion object{
        val TERMS_FOR_RESEARCH_API = "TermsForResearchApi"
    }



    lateinit var calendarFragment : CalendarFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val toolbar = findViewById<Toolbar>(R.id.toolbar_search_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Recuperation of the Fragment into the Search Activity
        calendarFragment = CalendarFragment()

        // the FindViewById from the Fragments collected into the Search Activity for Treatment before request HTTP to the server
        val editTextForSearch = findViewById<EditText>(R.id.edit_query)
        val button = findViewById<Button>(R.id.button_search)

//         Begin Date
//         End date


        var checkedArt : String
        var checkedPolitics : String
        var checkedBusiness : String
        var checkedSport  : String
        var checkedEntrepreneurs  : String
        var checkedTravel  : String




        // OnClick of the button Search
        button.setOnClickListener(View.OnClickListener {
            val querytext = editTextForSearch.text


            var artsChecked : Boolean = checkBoxArts.isChecked
            if (artsChecked == true) { checkedArt = "Arts" } else {checkedArt = ""}

            val politicsChecked : Boolean = checkBoxPolitics.isChecked
            if (politicsChecked == true) { checkedPolitics = "Politics" } else {checkedPolitics = ""}

            val businessChecked : Boolean = checkBoxBusiness.isChecked
            if (businessChecked == true) { checkedBusiness = "Business" } else {checkedBusiness = ""}

            val sportChecked : Boolean = checkBoxSport.isChecked
            if (sportChecked == true) { checkedSport = "Sport" } else {checkedSport = ""}

            val entrepreneursChecked : Boolean = checkBoxEntrepreneurs.isChecked
            if (entrepreneursChecked == true) { checkedEntrepreneurs = "Entrepreneurs" } else {checkedEntrepreneurs = ""}

            val travelChecked : Boolean = checkBoxTravel.isChecked
            if (travelChecked == true) { checkedTravel = "Travel" } else {checkedTravel = ""}


//            var begin = calendarFragment.entryDate
//            var end = calendarFragment.endDate


            Toast.makeText(this, "Search Button Clicked", Toast.LENGTH_SHORT).show()
            Log.i("Texte affiché ", " $querytext, $checkedArt, $checkedPolitics, $checkedBusiness, $checkedSport, $checkedEntrepreneurs, $checkedTravel")

            //     Log.i("BEG", " $begin ")

            var termsForResearchApi = " $querytext, $checkedArt, $checkedPolitics, $checkedBusiness, $checkedSport, $checkedEntrepreneurs, $checkedTravel"

           var intent = Intent(button.context, ResultSearchActivity::class.java)
            // To add some putExtra with all the strategic words to
            intent.putExtra(TERMS_FOR_RESEARCH_API, "$termsForResearchApi")
            button_search.context.startActivity(intent)


        })

    }
}
