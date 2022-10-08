package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class dash extends AppCompatActivity {

    LoginAction loginAction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "LOGIN";
    long loginTime;
    String loginDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loginAction = new LoginAction(this);

        TextView dash_name = findViewById(R.id.dash_name);
        TextView dash_city = findViewById(R.id.dash_city);
        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dash_name.setText(sharedPreferences.getString("UNAME",""));
        dash_city.setText(sharedPreferences.getString("CITY",""));

        loginTime = sharedPreferences.getLong("loginTime",0);
        loginDate = sharedPreferences.getString("loginDate","");
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        LinearLayout x0 = (LinearLayout) findViewById(R.id.l0);
        LinearLayout x1 = (LinearLayout) findViewById(R.id.l1);
        LinearLayout x2 = (LinearLayout) findViewById(R.id.l2);
        LinearLayout x3 = (LinearLayout) findViewById(R.id.l3);
        LinearLayout x4 = (LinearLayout) findViewById(R.id.l4);
        LinearLayout x5 = (LinearLayout) findViewById(R.id.l5);
        LinearLayout x6 = (LinearLayout) findViewById(R.id.l6);
        LinearLayout x7 = (LinearLayout) findViewById(R.id.l7);

            x0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iin = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(iin);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            x1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iin = new Intent(getApplicationContext(), mainyojna.class);
                    startActivity(iin);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            x2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iin = new Intent(getApplicationContext(), MainActivity3.class);
                    startActivity(iin);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            x3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iin = new Intent(getApplicationContext(), DisplayStory.class);
                    startActivity(iin);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), news.class);
                startActivity(iin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        x5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iin = new Intent(getApplicationContext(), cropprotect.class);
                startActivity(iin);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
            x6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iin = new Intent(getApplicationContext(), userProfile.class);
                    startActivity(iin);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
            x7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(dash.this);
                    // Setting Alert Dialog Title
                    alertDialogBuilder.setTitle("બહાર નીકળવાની પુષ્ટિ કરો!");
                    // Icon Of Alert Dialog
                    alertDialogBuilder.setIcon(R.drawable.logo);
                    // Setting Alert Dialog Message
                    alertDialogBuilder.setMessage("શું તમે ખરેખર લોગ આઉટ કરવા માંગો છો?");
                    alertDialogBuilder.setCancelable(false);

                    alertDialogBuilder.setPositiveButton("હા", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            loginAction.logout();
                            finish();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("ના", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alertDialogBuilder.setNeutralButton("રદ કરો", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            });

    }
    private void AlertMessage(Context context, String str) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setIcon(R.drawable.logo);
        alert.setTitle("Confirm Exit !");
        alert.setMessage(str);
        alert.setPositiveButton("Yes", (dialogInterface, i) -> {
            loginAction.logout();
            finish();
        });
        alert.setNegativeButton("Not Now",null);
        alert.show();
    }

    private void AlertMessageLogout(Context context, String str) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.logo);
        alert.setTitle("Alert !");
        alert.setMessage(str);
        alert.setPositiveButton("OK", (dialogInterface, i) -> {
            loginAction.logout();
            finish();
        });
        alert.show();
        alert.setCancelable(false);

    }

    public static boolean getTimeAfter30Minutes(long storedTime) {
        long currentTime =  System.currentTimeMillis();
        long updateTime = storedTime + (1000 * 60 * 30);
        if(currentTime > updateTime){
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.text_it)
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialogInterface, i) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

}