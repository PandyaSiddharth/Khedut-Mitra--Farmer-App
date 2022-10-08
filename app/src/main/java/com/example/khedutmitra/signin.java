package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class signin extends AppCompatActivity {

    LoginAction loginAction;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefName = "LOGIN";
    String valUser = "";
    String loginTime;
    TextInputEditText email, pwd;
    Button signup_btn,forgotpwd_btn;
    ProgressDialog progressDialog;
    private static final String URL = "https://multiple-explosion.000webhostapp.com/login.php?action_pg=Login_Auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().hide();
        forgotpwd_btn =findViewById(R.id.forgotpwd_btn);
        loginAction = new LoginAction(this);
        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        valUser = sharedPreferences.getString("EMAIL","");

        if(LoggedIn()){
            Intent intent = new Intent(this,dash.class);
            startActivity(intent);
            finish();
        }
        if (!valUser.equals(""))
        {
            Intent intent = new Intent(this,dash.class);
            startActivity(intent);
            finish();
        }
        else
        {
            email = findViewById(R.id.s_email);
            pwd = findViewById(R.id.s_password);

            findViewById(R.id.s_btn).setOnClickListener(view -> {
                if (validateUser()){
                    signinUser();
                }
            });
        }

        signup_btn = findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent u= new Intent(getApplicationContext(),signup.class);
                startActivity(u);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        forgotpwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userforgotpwd();
            }
        });
    }

    private void userforgotpwd() {
        View forgot_password_layout = LayoutInflater.from(this).inflate(R.layout.forgot_password,null);
        final EditText forgot_Email = forgot_password_layout.findViewById(R.id.forgot_email);
        Button btnForgotPass = forgot_password_layout.findViewById(R.id.forgot_password_btn);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setView(forgot_password_layout);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                final String email = forgot_Email.getText().toString().trim();
                if(email.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "enter a email", Toast.LENGTH_LONG).show();
                }else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.FORGOT_PASSWORD_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        String mail = object.getString("mail");
                                        if(mail.equals("send")){
                                            progressDialog.dismiss();
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Email are send Successfully", Toast.LENGTH_LONG).show();
                                        }else{
                                            progressDialog.dismiss();

                                            Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                        protected Map<String,String> getParams() throws AuthFailureError{
                            HashMap<String,String> forgotparams = new HashMap<>();
                            forgotparams.put("email",email);
                            return forgotparams;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(signin.this);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    private boolean LoggedIn() {

        SharedPreferences prefs = getSharedPreferences("User", Activity.MODE_PRIVATE);
        String username = prefs.getString("EMAIL", "");
        if (username.isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validateUser(){

        final String uemail = email.getText().toString().trim();
        final String upwd = pwd.getText().toString().trim();

        if (uemail.isEmpty()) {
            email.requestFocus();
            email.setError("Please enter Email");
            return false;
        }

        if(upwd.isEmpty()){
            pwd.requestFocus();
            pwd.setError("Please enter Password");
            return false;
        }

        return true;
    }

    private void signinUser() {

        final String uemail = email.getText().toString().trim();
        final String upwd = pwd.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String message = jsonObject.getString("message");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (message.equals("success")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String uid = object.getString("uid").trim();
                                    String uname = object.getString("uname").trim();
                                    String email = object.getString("email").trim();
                                    String city = object.getString("city").trim();
                                    String pwd = object.getString("pwd").trim();
                                    String mobile = object.getString("mobile").trim();

                                    loginAction.createSession(uid,uname,mobile,email,pwd,city);

                                    Intent intent = new Intent(this,dash.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else if(message.equals("error")){
                                AlertMessage(signin.this,"You're not registered user.");
                            }
                        } catch (JSONException e) {
                            Toast.makeText(signin.this, "Something went wrong.. Please try again."+e, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(signin.this, "No Internet connection.", Toast.LENGTH_SHORT).show())
            {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("uname", uemail);
                    params.put("pwd", upwd);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

    }

    private void AlertMessage(Context context, String str) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(R.drawable.ic_baseline_info);
        alert.setTitle("Info!!!");
        alert.setMessage(str);
        alert.setPositiveButton("OK", (dialogInterface, i) -> {
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo)
                .setMessage("શું તમે ખરેખર બહાર નીકળવા માંગો છો?")
                .setPositiveButton("હા", (dialogInterface, i) -> finish())
                .setNegativeButton("ના", null)
                .show();
    }

}