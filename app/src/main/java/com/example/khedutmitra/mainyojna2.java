package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class mainyojna2 extends AppCompatActivity {
    ImageView img;
    TextView tv1,tv2,tv3,tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainyojna2);
        img=(ImageView)findViewById(R.id.desc_img);
        tv1=(TextView)findViewById(R.id.desc_header);
        tv2=(TextView)findViewById(R.id.desc_date);
        tv3=(TextView)findViewById(R.id.desc_status);
        tv4=(TextView)findViewById(R.id.desc_link);

        img.setImageResource(getIntent().getIntExtra("imagename",0));
        tv1.setText(getIntent().getStringExtra("header"));
        tv2.setText(getIntent().getStringExtra("date"));
        tv3.setText(getIntent().getStringExtra("status"));
        tv4.setText(getIntent().getStringExtra("link"));
    }
}