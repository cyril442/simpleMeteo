package cyril.cieslak.mymynews

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    val webViewClient: WebViewClient = object : WebViewClient() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_webview_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        webView = findViewById(R.id.web_view)
        webView.webViewClient = webViewClient

        val settings = webView.settings
        settings.javaScriptEnabled = true

       webView.setBackgroundColor(Color.GREEN)



        val link = intent.getStringExtra(ItemNewsAdapter.ViewHolder.URL_ARTICLE_LINK)

        webView.loadUrl(link)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
