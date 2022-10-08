package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class news18 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news18);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        WebView webView = findViewById(R.id.web4);
        webView.loadUrl("https://gujarati.news18.com/tag/farmer/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}