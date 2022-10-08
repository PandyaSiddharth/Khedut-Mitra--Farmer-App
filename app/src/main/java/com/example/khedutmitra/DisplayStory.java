package com.example.khedutmitra;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayStory extends AppCompatActivity {

    TextView tt;
    ArrayList<Post> post;
    PostAdapter postAdapter;
    RecyclerView recyclerView;
    private static final String GET_POST_URL = "https://multiple-explosion.000webhostapp.com/post_master.php?action_pg=Post_List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_story);

        recyclerView = findViewById(R.id.postView);
        post = new ArrayList<>();

        LinearLayoutManager llm1 = new LinearLayoutManager(this);
        llm1.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(llm1);

        getAllPost();
    }
    private void getAllPost() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_POST_URL,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String message = jsonObject.getString("message");
                        JSONArray jsonArray = jsonObject.getJSONArray("post_master");

                        if (message.equals("success")) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id").trim();
                                String title = object.getString("title").trim();
                                String img_url = object.getString("img_url").trim();
                                String description = object.getString("description").trim();
                                String created_by = object.getString("created_by").trim();
                                Post p = new Post(id, img_url, title, description, created_by);
                                post.add(p);

                                postAdapter = new PostAdapter(post,this);
                                recyclerView.setAdapter(postAdapter);
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

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_user:
                finish();
                Intent iin = new Intent(getApplicationContext(),createpost.class);
                startActivity(iin);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
        return (super.onOptionsItemSelected(item));
    }
}