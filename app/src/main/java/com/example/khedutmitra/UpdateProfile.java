package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
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

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    LoginAction loginAction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "LOGIN";
    String email;
    TextView upfname, upemail, upmobile, upcity, uppwd;
    private static final String FETCH_USER_URL = "https://multiple-explosion.000webhostapp.com/fetch_user.php?action_pg=Fetch_User&&email=";
    private static final String UPDATE_USER_URL = "https://multiple-explosion.000webhostapp.com/update_user.php?action_pg=Update_User";
    Button btnupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        upfname = findViewById(R.id.up_name);
        upmobile = findViewById(R.id.up_number);
        upemail = findViewById(R.id.up_email);
        upcity = findViewById(R.id.up_city);
        uppwd = findViewById(R.id.up_password);

        loginAction = new LoginAction(this);
        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        email = sharedPreferences.getString("EMAIL","");

        btnupdate = findViewById(R.id.updatebtn);
        getUserDetail();
    }

    private void getUserDetail(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_USER_URL+email,
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

                                upfname.setText(uname);
                                upmobile.setText(mobile);
                                upemail.setText(email);
                                upcity.setText(city);
                                uppwd.setText(pwd);

                                btnupdate.setOnClickListener(view -> {
                                    if (validateUserDetails()) {
                                        updateUser();
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

    private boolean validateUserDetails() {

        String uname = upfname.getText().toString();
        String mobile = upmobile.getText().toString();
        String email = upemail.getText().toString();
        String city = upcity.getText().toString();
        String pwd = uppwd.getText().toString();

        if (uname.isEmpty()) {
            upfname.requestFocus();
            upfname.setError("Please enter your name");
            return false;
        }
        if (email.isEmpty()) {
            upemail.requestFocus();
            upemail.setError("Please enter your email");
            return false;
        }
//        else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            upemail.requestFocus();
//            upemail.setError("Please enter valid email address");
//            return false;
//        }
        if (city.isEmpty()) {
            upcity.requestFocus();
            upcity.setError("Please enter your City");
            return false;
        }
        if (mobile.isEmpty()) {
            upmobile.requestFocus();
            upmobile.setError("Please enter the Mobile Number.");
            return false;
        }
        else if(!mobile.matches("^[0-9]*$") || mobile.length()!=10){
            upmobile.requestFocus();
            upmobile.setError("Please enter valid 10 digit Mobile number");
            return false;
        }
        if (pwd.isEmpty()) {
            uppwd.requestFocus();
            uppwd.setError("Please enter your Password");
            return false;
        }

        return true;
    }

    public void updateUser(){

        String uname = upfname.getText().toString().trim();
        String umobile = upmobile.getText().toString().trim();
        String uemail = upemail.getText().toString().trim();
        String ucity = upcity.getText().toString().trim();
        String upwd = uppwd.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPDATE_USER_URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");

                        if(message.equals("success")){
                            Toast.makeText(UpdateProfile.this, "Details Updated Successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(getApplicationContext(),dash.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        }
                        else if(message.equals("error")){
                            Toast.makeText(UpdateProfile.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    catch (JSONException e){
                        Toast.makeText(UpdateProfile.this, "Something went wrong..Please try again.!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(UpdateProfile.this, "No Internet connection.", Toast.LENGTH_SHORT).show())
        {
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<>();
                params.put("uid", sharedPreferences.getString("UID",""));
                params.put("uname",uname);
                params.put("umobile",umobile);
                params.put("uemail",uemail);
                params.put("ucity",ucity);
                params.put("upwd",upwd);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UpdateProfile.this);
        requestQueue.add(stringRequest);
    }

}