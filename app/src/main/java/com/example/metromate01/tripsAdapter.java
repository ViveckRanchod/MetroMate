package com.example.metromate01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tripsAdapter extends RecyclerView.Adapter<tripsAdapter.MyViewHolder> {

        Context context;
        ArrayList<trips> list;
        ArrayList<trips> filterList;


        public tripsAdapter(Context context, ArrayList<trips> list) {
                this.context = context;
                this.list = list;
                this.filterList= new ArrayList<>(list);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v =LayoutInflater.from(context).inflate(R.layout.trip_card,parent,false);
                return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                trips Trips = list.get(position);
                holder.arvTime.setText(Trips.getArrivalTime());
                holder.depTime.setText(Trips.getDepartureTime());
                holder.cashPrice.setText(Trips.getCashPrice());
                holder.tagPrice.setText(Trips.getTagPrice());
                holder.busNo.setText(Trips.getBusNo());

        }

        @Override
        public int getItemCount() {
                // return list.size();
                return filterList.size();
        }
        //check if the database has the depature time the user entered:

        public void filterInputTime(String time_input){
                //empty for every search
                filterList.clear();

                for(trips filter: list){
                        String deptTimeDB = filter.getDepartureTime();
                        if(deptTimeDB.equals(time_input)){
                                filterList.add(filter);
                        }
                }
        }

        //get the card views that have a time closest to the database entries:
        public void filterClosestTimes(String before, String after){
                //empty for every search
                filterList.clear();

                for(trips filterBA: list){
                        String deptTimeBA = filterBA.getDepartureTime();
                        if(deptTimeBA.equals(before)&&deptTimeBA.equals(after)){
                                filterList.add(filterBA);
                        }
                }

        }
        public void reset() {
                filterList.clear();
                filterList.addAll(list);
                // notifyDataSetChanged();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{

                TextView arvTime, depTime, cashPrice, tagPrice, busNo;

                public MyViewHolder(@NonNull View itemView) {
                        super(itemView);
                        arvTime = itemView.findViewById(R.id.TVarriveTime);
                        depTime = itemView.findViewById(R.id.TVdepartTime);
                        cashPrice = itemView.findViewById(R.id.TVcashPrice);
                        tagPrice = itemView.findViewById(R.id.TVtagPrice);
                        busNo = itemView.findViewById(R.id.TVbusNumber);

                }
        }
}

