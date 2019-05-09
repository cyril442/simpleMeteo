package cyril.cieslak.mymynews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ResultSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_search)

        val termsForResearchApiFromSearchActivity = intent.getStringExtra(SearchActivity.TERMS_FOR_RESEARCH_API)
        Toast.makeText(this, termsForResearchApiFromSearchActivity, Toast.LENGTH_LONG).show()
    }
}
