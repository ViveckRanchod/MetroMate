package com.example.metromate01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class tripsAdapterV extends RecyclerView.Adapter<tripsAdapterV.MyViewHolder> {

    private Context context;
    private HashMap<String, viveckTrips> tripsMap;

    public tripsAdapterV(Context context, HashMap<String, viveckTrips> tripsMap) {
        this.context = context;
        this.tripsMap = tripsMap;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.constraint_trip, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String key = (String) tripsMap.keySet().toArray()[position];
        viveckTrips trip = tripsMap.get(key);

        // Set the retrieved data to the appropriate views
        holder.busNumber.setText(String.valueOf(trip.getBus_no()));
        holder.tagPrice.setText(trip.getPrice());
    }

    @Override
    public int getItemCount() {
        return tripsMap.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView busNumber, tagPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            busNumber = itemView.findViewById(R.id.busNumberTV);
            tagPrice = itemView.findViewById(R.id.tagPriceTV);
        }
    }
}
