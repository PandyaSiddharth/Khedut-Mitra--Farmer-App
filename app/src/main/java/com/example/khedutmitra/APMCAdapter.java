package com.example.khedutmitra;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class APMCAdapter extends RecyclerView.Adapter<APMCAdapter.ApmcVH>{
    ArrayList<APMC> apmcs;
    Context context;
    int count;
    private static final String GET_URL = "https://api.data.gov.in/resource/9ef84268-d588-465a-a308-a864a43d0070?api-key=579b464db66ec23bdd000001987c65666f9c49656f0f9ef4fa3650e7&format=json&offset=0&limit=7000";

    APMCAdapter(ArrayList<APMC> apmcs, Context context) {
        this.apmcs = apmcs;
        this.context = context;
    }
    @NonNull
    @Override
    public APMCAdapter.ApmcVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.list_row,parent,false);
        ApmcVH avh = new ApmcVH(view);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull APMCAdapter.ApmcVH holder, int position) {
        final APMC a = apmcs.get(position);

        holder.state.setText(a.getState());
        holder.district.setText(a.getDistrict());
        holder.market.setText(a.getMarket());

        holder.details.setStretchAllColumns(true);
        holder.details.setShrinkAllColumns(true);
        holder.details.setPadding(15,10,15,10);

        TableRow detailsTitle =  new TableRow(context);
        TextView commodityTitle = new TextView(context);
        commodityTitle.setLayoutParams(new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 0));
        commodityTitle.setText("Commodity");
        commodityTitle.setPadding(25,25,25,25);
        commodityTitle.setTypeface(Typeface.DEFAULT_BOLD);
        commodityTitle.setTextSize(16);
        commodityTitle.setGravity(Gravity.CENTER_VERTICAL);
        commodityTitle.setBackgroundResource(R.drawable.table_border);
        detailsTitle.addView(commodityTitle);

        TextView maxTitle = new TextView(context);
        maxTitle.setLayoutParams(new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 0));
        maxTitle.setText("Max");
        maxTitle.setPadding(25,25,25,25);
        maxTitle.setTypeface(Typeface.DEFAULT_BOLD);
        maxTitle.setTextSize(16);
        maxTitle.setGravity(Gravity.CENTER_VERTICAL);
        maxTitle.setBackgroundResource(R.drawable.table_border);
        detailsTitle.addView(maxTitle);

        TextView minTitle = new TextView(context);
        minTitle.setLayoutParams(new TableRow.LayoutParams
                (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 0));
        minTitle.setText("Min");
        minTitle.setPadding(25,25,25,25);
        minTitle.setBackgroundResource(R.drawable.table_border);
        minTitle.setTypeface(Typeface.DEFAULT_BOLD);
        minTitle.setTextSize(16);
        minTitle.setGravity(Gravity.CENTER_VERTICAL);
        detailsTitle.addView(minTitle);

        holder.details.addView(detailsTitle);

        count = 0;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_URL,
                response -> {
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("records");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            if(holder.market.getText().equals(object.getString("market"))){

                                TableRow detailsRow =  new TableRow(context);

                                TextView commodityDetails = new TextView(context);
                                commodityDetails.setLayoutParams(new TableRow.LayoutParams
                                        (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 0));
                                commodityDetails.setText(object.getString("commodity"));
                                commodityDetails.setPadding(25,25,25,25);
                                commodityDetails.setTextSize(16);
                                commodityDetails.setBackgroundResource(R.drawable.table_border);
                                detailsRow.addView(commodityDetails);

                                TextView maxDetails = new TextView(context);
                                maxDetails.setLayoutParams(new TableRow.LayoutParams
                                        (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 0));
                                maxDetails.setText(object.getString("min_price"));
                                maxDetails.setPadding(25,25,25,25);
                                maxDetails.setTextSize(16);
                                maxDetails.setBackgroundResource(R.drawable.table_border);
                                detailsRow.addView(maxDetails);

                                TextView minDetails = new TextView(context);
                                minDetails.setLayoutParams(new TableRow.LayoutParams
                                        (TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT, 0));
                                minDetails.setText(object.getString("max_price"));
                                minDetails.setPadding(25,25,25,25);
                                minDetails.setBackgroundResource(R.drawable.table_border);
                                minDetails.setTextSize(16);
                                detailsRow.addView(minDetails);

                                holder.details.addView(detailsRow);

                            }
                        }

                    } catch (JSONException e) {
                        Toast.makeText(context, "Something went wrong.. Please try again.!", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(context, "No Internet connection.!", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return apmcs.size();
    }

    class ApmcVH extends RecyclerView.ViewHolder{

        TextView state,district,market;
        TableLayout details;

        public ApmcVH(@Nullable View v){
            super(v);
            state = v.findViewById(R.id.state);
            district = v.findViewById(R.id.district);
            market = v.findViewById(R.id.market);
            details = v.findViewById(R.id.details);

        }
    }
}
