package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private static final String URL = "https://multiple-explosion.000webhostapp.com/register_user.php?action_pg=signup";

    TextInputEditText regName, regNumber, regEmail, regCity, regPassword;
    Button regBtn, regToLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //Hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regNumber = findViewById(R.id.reg_number);
        regEmail = findViewById(R.id.reg_email);
        regCity = findViewById(R.id.reg_city);
        regPassword = findViewById(R.id.reg_password);

        regToLoginBtn = (Button) findViewById(R.id.reg_login_btn);
        regBtn = (Button) findViewById(R.id.reg_btn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUserDetails()) {
                    registerUser();
                }
            }
        });
        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent u= new Intent(getApplicationContext(),signin.class);
                startActivity(u);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private boolean validateUserDetails() {

        String uname = regName.getText().toString();
        String mobile = regNumber.getText().toString();
        String email = regEmail.getText().toString();
        String city = regCity.getText().toString();
        String pwd = regPassword.getText().toString();

        if (uname.isEmpty()) {
            regName.requestFocus();
            regName.setError("Please enter your name");
            return false;
        }
        if (email.isEmpty()) {
            regEmail.requestFocus();
            regEmail.setError("Please enter your email");
            return false;
        }
//        else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            regEmail.requestFocus();
//            regEmail.setError("Please enter valid email address");
//            return false;
//        }
        if (city.isEmpty()) {
            regCity.requestFocus();
            regCity.setError("Please enter your City");
            return false;
        }
        if (mobile.isEmpty()) {
            regNumber.requestFocus();
            regNumber.setError("Please enter the Mobile Number.");
            return false;
        }
        else if(!mobile.matches("^[0-9]*$") || mobile.length()!=10){
            regNumber.requestFocus();
            regNumber.setError("Please enter valid 10 digit Mobile number");
            return false;
        }
        if (pwd.isEmpty()) {
            regPassword.requestFocus();
            regPassword.setError("Please enter your Password");
            return false;
        }

        return true;
    }

    private void registerUser() {

        String uname = regName.getText().toString();
        String mobile = regNumber.getText().toString();
        String email = regEmail.getText().toString();
        String city = regCity.getText().toString();
        String pwd = regPassword.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");

                        if(message.equals("success")){
                            Toast.makeText(signup.this, "User created Successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent u= new Intent(getApplicationContext(),signin.class);
                            startActivity(u);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        else if(message.equals("error")){
                            Toast.makeText(signup.this, "User already registered.", Toast.LENGTH_SHORT).show();
//                            finish();
                        }
                    }
                    catch (JSONException e){
                        Toast.makeText(signup.this, "Something went wrong..Please try again.!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(signup.this, "No Internet connection.", Toast.LENGTH_SHORT).show())
        {
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<>();
                params.put("uname",uname);
                params.put("mobile",mobile);
                params.put("email",email);
                params.put("city",city);
                params.put("pwd",pwd);
                params.put("created_at",dateFormat.format(new Date()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(signup.this);
        requestQueue.add(stringRequest);

    }
}