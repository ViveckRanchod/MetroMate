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
        ArrayList<trips> filterList;//leave it here


        public tripsAdapter(Context context, ArrayList<trips> list) {
                this.context = context;
                this.list = list;
                this.filterList= new ArrayList<>(list);
                //remove filterList from the constructor and parameter
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
               
                //add return list.size() and remove the bottom
                return filterList.size();
        }
        //check if the database has the depature time the user entered:

        public void filterInputTime(String time_input){
                // ArrayList<trips> filterList = new ArrayList(); ADD THIS
                //remove filterList.clear();
                filterList.clear();

                for(trips filter: list){
                        String deptTimeDB = filter.getDepartureTime();
                        //CHANGE CONDITION IN THIS IF
                        /*if(filter.getDepatureTime().equals(time_input))*/
                        if(deptTimeDB.equals(time_input)){
                                filterList.add(filter);
                        }
                }
                //INCLUDE: passData(filterList);
        }

       /*ADD THIS COMMENT AND METHOD:
        //set list to filtered data
       public void passData(ArrayList<trips> data){
                list = data;
                notifyDataSetChanged();
        }*/


        //get the card views that have a time closest to the database entries:
        public void filterClosestTimes(String before, String after){
                // ArrayList<trips> filterList = new ArrayList(); ADD THIS
                //remove filterList.clear();
                filterList.clear();

                for(trips filterBA: list){
                        String deptTimeBA = filterBA.getDepartureTime();
                        //CHANGED THIS FROM: if(deptTimeBA.equals(before)||deptTimeBA.equals(after)){
                        // filterList.add(filterBA);} TO->
                        if(deptTimeBA.equals(before)) {
                                filterList.add(filterBA);
                        }
                        if(deptTimeBA.equals(after)){
                                filterList.add(filterBA);
                        }
                }
                //INCLUDE: passData(filterList);

        }
        public void reset() {
                //remove both filter codes and leave the notifyDataChange code
                filterList.clear();
                filterList.addAll(list);
              notifyDataSetChanged();
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

