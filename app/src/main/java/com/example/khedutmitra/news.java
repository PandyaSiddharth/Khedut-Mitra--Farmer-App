package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class news extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        LinearLayout xx0 = (LinearLayout) findViewById(R.id.n0);
        LinearLayout xx1 = (LinearLayout) findViewById(R.id.n1);
        LinearLayout xx2 = (LinearLayout) findViewById(R.id.n2);
        LinearLayout xx3 = (LinearLayout) findViewById(R.id.n3);
        xx0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), krushi.class);
                startActivity(iin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        xx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), tvnine.class);
                startActivity(iin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        xx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), oneindia.class);
                startActivity(iin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        xx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), news18.class);
                startActivity(iin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}