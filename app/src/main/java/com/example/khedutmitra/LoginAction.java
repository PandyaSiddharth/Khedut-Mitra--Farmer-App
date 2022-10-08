package com.example.khedutmitra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LoginAction {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String UID = "UID";
    public static final String UNAME = "UNAME";
    public static final String MOBILE = "MOBILE";
    public static final String EMAIL = "EMAIL";
    public static final String PWD = "PWD";
    public static final String CITY = "CITY";

    public LoginAction(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String uid, String uname, String mobile, String email, String pwd, String city){
        editor.putBoolean(LOGIN,true);
        editor.putString(UID,uid);
        editor.putString(UNAME,uname);
        editor.putString(EMAIL,email);
        editor.putString(PWD,pwd);
        editor.putString(CITY,city);
        editor.putString(MOBILE, mobile);
        editor.apply();
    }

    public void logout(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(context,signin.class);
        context.startActivity(intent);
    }

}
