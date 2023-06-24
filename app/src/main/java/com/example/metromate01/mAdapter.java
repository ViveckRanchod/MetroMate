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

public class mAdapter extends RecyclerView.Adapter<mAdapter.mViewHolder> {

    Context context;
    ArrayList<Object> arr_dep;

    public mAdapter(Context context, ArrayList<Object> time_price){
        this.context = context;
        this.arr_dep = time_price;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_page_bottom_sheet,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        Trips selected_time = (Trips) arr_dep.get(position);
        holder.bus_no.setText(selected_time.getBus_no());
        holder.arrival.setText(selected_time.getArrival());
        holder.tagPrice.setText(selected_time.getPrice());
    }

    @Override
    public int getItemCount() {
        return arr_dep.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder{

        TextView tagPrice, bus_no, arrival;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);

           /* tagPrice = itemView.findViewById(R.id.tagPrice);
            arrival = itemView.findViewById(R.id.arrival_time);
            bus_no = itemView.findViewById(R.id.bus_no);*/
        }
    }
}
