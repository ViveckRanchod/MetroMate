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

        public tripsAdapter(Context context, ArrayList<trips> list) {
                this.context = context;
                this.list = list;
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
                return list.size();
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
/*
Spinner spinner1, spinner2;
EditText time;
Button search;


 time = FindViewById(R.id.editTextTime2);
 spinner1 = findViewById(R.id.spinner);
 spinner2 = findViewById(R.id.spinner);
 search = findViewById(R.id.button);

search.setOnClickListener(new View.OnclickLister(){
@Override
public void onClick(){
 //get current values:
 int spinner1_id = Sspinner1.getItemIdAtPosition(position);
 int spinner2_id = Sspinner2.getItemIdAtPosition(position);
 Sspinner1 = spinner1.getSelectedItem().toString();
 Sspinner2 = spinner2.getSelectedItem().toString();
 Stime = time.getText().toString();

//declare objects:
 Database db = new Database();
ArrayList<String> deptTimeList = new ArrayList<>();

 //convert string input into a time object
 DateTimeFormatter format_time = DateTimeFormatter.ofPattern("HH:mm");
 LocalTime Ttime = LocalTime.parse(Stime, format_time);

if(!Sspinner1.equals(" ")&& !Sspinner2.equals(" ")&& !Stime.equals(" ") && spinner1_id!=spinner2_id)
{

	//convert selections to a list to compare it to the database entries:

	ArrayList<String> arrival_depature= new ArrayList<>();
	arrival_depature.add(Sspinner1);
	arrival_depature.add(Sspinner2);

	//convert db list of depature times to local time objects:
	for(String timeList: db.getChildren("trips", "depatureStop")){
		localTime time = LocalTime.parse(timeList);
		deptTimeList.add(time);
	}

	//if the db method returns true, check if the time input matches any of the time values in the db or if it doesnt then get the closest time values before and after said value:

	if(db.searchDatabaseChild("trips", arrival_depature)==true)
	{
		if(db.searchDatabaseChild("trips","deptTimeList")==true)
		{
			//paste time conversions and set conditions
			// in the conditions paste hide code for the
			// the cards
		}
	}else{
		Toast.makeText(this,"Please ensure all fields are selected and inputs are filled", Toast.LENGTH_SHORT).show();

	}

// convert depature time list into localtime and get the closest time before and after then convert those times back into string ann search the list for them
	//display the recycler view according to the spinner 1 and 2, whether the time entered is in there if not get a list of the closests ones and display those and hide the others

//hide other cards
for (int i = 0; i < getItemCount(); i++) {
            ViewHolder viewHolder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                if (condition) {
                    viewHolder.itemView.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.itemView.setVisibility(View.GONE);
                }
            }
        }//

}
        else{
        Toast.makeText(this,"Please ensure the depature and arrival stop are selected and a depature time is filled", Toast.LENGTH_SHORT).show();
        }


        }

        });

*/
