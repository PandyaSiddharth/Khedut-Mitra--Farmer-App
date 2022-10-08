package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class oneindia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneindia);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        WebView webView = findViewById(R.id.web3);
        webView.loadUrl("https://gujarati.oneindia.com/agriculture/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}