package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class cropprotect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropprotect);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}