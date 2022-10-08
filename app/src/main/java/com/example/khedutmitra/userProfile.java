package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class userProfile extends AppCompatActivity {

    LoginAction loginAction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "LOGIN";
    String email;
    TextView tvfname, tvemail, tvmobile, tvcity, tvpwd;
    Button btnedit;
    private static final String GET_USER_URL = "https://multiple-explosion.000webhostapp.com/fetch_user.php?action_pg=Fetch_User&&email=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        loginAction = new LoginAction(this);
        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("EMAIL","");

        tvfname = findViewById(R.id.tvfname);
        tvmobile = findViewById(R.id.tvmobile);
        tvemail = findViewById(R.id.tvemail);
        tvcity = findViewById(R.id.tvcity);
        tvpwd = findViewById(R.id.tvpwd);
        btnedit = findViewById(R.id.btnedit);

        getUserDetail();
    }

    private void getUserDetail(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_USER_URL+email,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String message = jsonObject.getString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("fetch_user");

                        if (message.equals("success")) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String uid = object.getString("id").trim();
                                String uname = object.getString("uname").trim();
                                String email = object.getString("email").trim();
                                String mobile = object.getString("mobile").trim();
                                String city = object.getString("city").trim();
                                String pwd = object.getString("pwd").trim();

                                tvfname.setText(uname);
                                tvmobile.setText(mobile);
                                tvemail.setText(email);
                                tvcity.setText(city);
                                tvpwd.setText(pwd);

                                btnedit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent iin = new Intent(getApplicationContext(), UpdateProfile.class);
                                        startActivity(iin);
                                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                    }
                                });
                            }
                        }
                        else if(message.equals("error")){
                            Toast.makeText(this, "No Record Found.!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(this, "Something went wrong.. Please try again.!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "No Internet connection.", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }
