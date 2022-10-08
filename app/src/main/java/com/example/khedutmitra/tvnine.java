package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class tvnine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvnine);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        WebView webView = findViewById(R.id.web2);
        webView.loadUrl("https://tv9gujarati.com/dhartiputra-agriculture");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }
}