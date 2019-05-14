package cyril.cieslak.mymynews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Toast

class ResultSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search)

        val termsForResearchApiFromSearchActivity = intent.getStringExtra(SearchActivity.TERMS_FOR_RESEARCH_API)
        Toast.makeText(this, termsForResearchApiFromSearchActivity, Toast.LENGTH_LONG).show()
        Log.i("texte", "$termsForResearchApiFromSearchActivity")

        val toolbar = findViewById<Toolbar>(R.id.toolbar_webview_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


    }
}
