package jp.co.quad.githubsearchkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class ResultDetail : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_detail)
        val repositoryName:String = intent.getStringExtra("repo_name")

        webView = findViewById<WebView>(R.id.web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Toast.makeText(this@ResultDetail, error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://github.com/" + repositoryName)
    }
}
