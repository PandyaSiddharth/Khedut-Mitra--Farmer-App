package com.example.khedutmitra;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class createpost extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    String PREF_NAME = "LOGIN";
    Button ssbtn, ss_submit;
    ImageView ssimg;
    Uri imguri;
    TextInputEditText sstitle, ssdesc;
    String prefName = "LOGIN";
    private static final String ADD_STORY_URL = "https://multiple-explosion.000webhostapp.com/add_story.php?action_pg=Add_Story";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);

        ssimg = findViewById(R.id.ssimg);
        sstitle = findViewById(R.id.sstitle);
        ssdesc = findViewById(R.id.ssdesc);
        ssbtn = findViewById(R.id.ssbtn);
        ss_submit = findViewById(R.id.ss_submit);

        sharedPreferences = getSharedPreferences(prefName,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ssbtn.setOnClickListener(fuelView -> ImagePicker.with(createpost.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start(1));

        ss_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateDetails()){
                    AddPost();
                } else{
                    Toast.makeText(createpost.this, "Please add all the Details.!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddPost(){
        Bitmap ss_img;
        ss_img = ((BitmapDrawable)ssimg.getDrawable()).getBitmap();
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        ss_img.compress(Bitmap.CompressFormat.WEBP,0, baos1);
        byte[] b1 = baos1.toByteArray();

        String encodedImage1 = Base64.encodeToString(b1, Base64.DEFAULT);
        String ss_title = sstitle.getText().toString();
        String ss_desc = ssdesc.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADD_STORY_URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");

                        if(message.equals("success")){
                            Toast.makeText(createpost.this, "Post added Successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else if(message.equals("error")){
                            Toast.makeText(createpost.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    catch (JSONException e){
                        Toast.makeText(createpost.this, "Something went wrong..Please try again.!"+e, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(createpost.this, "No Internet connection."+error, Toast.LENGTH_SHORT).show())
        {
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<>();

                params.put("img_url",encodedImage1);
                params.put("title",ss_title);
                params.put("description",ss_desc);
                params.put("created_by",sharedPreferences.getString("UNAME",""));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(createpost.this);
        requestQueue.add(stringRequest);
    }

    private boolean validateDetails(){
        String ss_title = sstitle.getText().toString();
        String ss_desc = ssdesc.getText().toString();
        if (imguri == null || ss_title.isEmpty() || ss_desc.isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            imguri = data.getData();
            ssimg.setImageURI(imguri);
        }
    }
}