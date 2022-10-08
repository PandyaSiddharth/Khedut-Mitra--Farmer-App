package com.example.khedutmitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {

    Spinner state, districts;
    Button btnSbn;
    ArrayList<APMC> apmcs;
    APMCAdapter apmcAdapter;
    String market = "";
    public static ArrayList<String> comList;
    public static ArrayList<String> maxList;
    public static ArrayList<String> minList;
    RecyclerView recyclerView;
    private static final String GET_URL = "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7&format=json&offset=0&limit=7000";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        state = findViewById(R.id.states);
        districts = findViewById(R.id.districts);
        comList = new ArrayList<>();
        maxList = new ArrayList<>();
        minList = new ArrayList<>();
        btnSbn = findViewById(R.id.btnsbn);

        ArrayAdapter stateAdapter = ArrayAdapter.createFromResource(this,R.array.states, R.layout.color_spinner_layout);
        stateAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        state.setAdapter(stateAdapter);

        ArrayAdapter districtAdapter = ArrayAdapter.createFromResource(this,R.array.districts, R.layout.color_spinner_layout);
        districtAdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        districts.setAdapter(districtAdapter);

        recyclerView = findViewById(R.id.recyclerView);
        apmcs = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm);

        btnSbn.setOnClickListener(view -> getAllDetails());
    }
    private void getAllDetails() {

        String s = state.getSelectedItem().toString();
        String d = districts.getSelectedItem().toString();

        apmcs.clear();
//        apmcAdapter = new APMCAdapter(apmcs,MainActivity3.this);
//        recyclerView.setAdapter(apmcAdapter);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL,
                response -> {
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("records");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String state = object.getString("state").trim();
                            String district = object.getString("district").trim();

                            if(s.equals(object.getString("state").trim()) && d.equals(object.getString("district"))){

                                if(!comList.contains(object.getString("market"))) {
                                    comList.add(object.getString("market"));
                                    APMC a = new APMC(state, district,object.getString("market") );
                                    apmcs.add(a);
                                    apmcAdapter = new APMCAdapter(apmcs, MainActivity3.this);
                                    recyclerView.setAdapter(apmcAdapter);
                                }

                            } else {
                                Toast.makeText(this, "No Records Available.!", Toast.LENGTH_SHORT).show();
                            }
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