package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    Animation t,b;
    ImageView i;
    TextView n;
    LottieAnimationView animationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Context context;
        t= AnimationUtils.loadAnimation(this, R.anim.top);
        b=AnimationUtils.loadAnimation(this, R.anim.bottom);

        i=findViewById(R.id.img);
        n=findViewById(R.id.t1);
        animationView = findViewById(R.id.tractor);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        animationView.setAnimation(animation);
        i.setAnimation(t);
        n.setAnimation(b);
        Thread t = new Thread()
        {
            public void run()
            {
                try {
                    sleep(5500);
                    Intent u= new Intent(getApplicationContext(),OnBoarding.class);
                    startActivity(u);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
