package com.example.metromate01;

import android.annotation.SuppressLint;
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


        public tripsAdapter(Context context, ArrayList<trips> list, ArrayList<trips> filterList) {
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



 time = FindViewById(R.id.editTextTime2);
 spinner1 = findViewById(R.id.spinner);
 spinner2 = findViewById(R.id.spinner);
 search = findViewById(R.id.button);

search.setOnClickListener(new View.OnclickLister(){
@Override
public void onClick()
        {
0        //get current values and inputs:

        int spinner1_id = Sspinner1.getItemIdAtPosition(position);
        int spinner2_id = Sspinner2.getItemIdAtPosition(position);
        Sspinner1 = spinner1.getSelectedItem().toString();
        Sspinner2 = spinner2.getSelectedItem().toString();
        Stime = time.getText().toString();

//declare objects:
        List<String> arrival_depature= new ArrayList<>();
        List<LocalTime> deptTimeList = new ArrayList<>();
        Database db = new Database();

        List<String> deptStopList = db.getChildren("trips", "depatureStop");


        if(!Sspinner1.isEmpty()&& !Sspinner2.isEmpty()&& !Stime.isEmpty()&& spinner1_id!=spinner2_id)
        {
        //convert selections to arrival_depature list entries

        arrival_depature.add(Sspinner1);
        arrival_depature.add(Sspinner2);

        //convert time input into local time object

        DateTimeFormatter format_time = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime Ttime_input = LocalTime.parse(Stime, format_time);

        //convert db list of depature times from db to local time objects:

        for(String timeDB: deptStopList){
        localTime timeFromDb = LocalTime.parse(timeDB);
        deptTimeList.add(timeFromDb);
        }

//check if the Ttime_input matches any of the time values in the deptTimeList or if it doesnt then get the closest time values before and after Ttime_input that are found in the deptTimeList and assign the before times to a list and after to a list then convert those lsists back to string:

        if(deptTimeList.contains(Ttime_input))
        {

        }
        else{

        }


        }else{
        Toast.makeText(this,"Please ensure all fields are selected and inputs are filled", Toast.LENGTH_SHORT).show();

        }

//display the recycler view according to the spinner 1 and 2, whether the time entered is in there if not get a list of the closests ones and display those and hide the others

/ hide other cards
for (int i = 0; i < getItemCount(); i++) {
            ViewHolder viewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                if (condition) {
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.itemView.setVisibility(View.GONE);
                }
            }
        }

        }
        else{
        Toast.makeText(this,"Please ensure the depature and arrival stop are selected and a depature time is filled", Toast.LENGTH_SHORT).show();
        }


        }

        });



