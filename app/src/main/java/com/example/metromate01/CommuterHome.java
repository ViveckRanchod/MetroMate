package com.example.metromate01;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommuterHome extends Fragment {
    Spinner spinner1, spinner2;
    EditText time;
    Button search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        time = view.findViewById(R.id.editTextTime2);
        spinner1 = view.findViewById(R.id.spinner);
        spinner2 = view.findViewById(R.id.spinner2);
        search = view.findViewById(R.id.button);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //get current values and inputs:
                String Sspinner1 = spinner1.getSelectedItem().toString();
                String Sspinner2 = spinner2.getSelectedItem().toString();
                int spinner1_id = spinner1.getSelectedItemPosition();
                int spinner2_id = spinner2.getSelectedItemPosition();

                String Stime = time.getText().toString();
                //declare objects:
                String StrclosestBefore=null ,  StrclosestAfter =null;
                List<LocalTime> deptTimeList = new ArrayList<>();
                ArrayList<trips> list = new ArrayList<>();
                ArrayList<trips> filterList = new ArrayList<>();

                tripsAdapter adapter = new tripsAdapter(getContext(), list, (ArrayList<trips>) filterList);
                Database db = new Database();



                List<String> deptStopList = db.getChildren("trips", "depatureStop");


                if (!Sspinner1.isEmpty() && !Sspinner2.isEmpty() && !Stime.isEmpty() && spinner1_id != spinner2_id) {

                    //check if the Ttime_input matches any of the time values in the deptTimeList or if it doesnt then get the closest time values
                    // before and after Ttime_input that are found in the deptTimeList and display the cards accordingly

                    if (deptStopList.contains(Stime)) {
                        adapter.filterInputTime(Stime);
                    } else {
                        //convert time input into local time object

                        DateTimeFormatter format_time = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            format_time = DateTimeFormatter.ofPattern("HH:mm");
                        }
                        LocalTime Ttime_input = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Ttime_input = LocalTime.parse(Stime, format_time);
                        }

                        //convert db list of depature times from db to local time objects:

                        for (String timeDB : deptStopList) {
                            LocalTime timeFromDb = null;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                timeFromDb = LocalTime.parse(timeDB, DateTimeFormatter.ofPattern("HH:mm"));
                            }
                            deptTimeList.add(timeFromDb);
                        }

                        // Find closest departure times to user input
                        DateTimeFormatter formatTime = null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            formatTime = DateTimeFormatter.ofPattern("HH:mm");
                        }
                        LocalTime closestBefore = null;
                        LocalTime closestAfter = null;
                        for (LocalTime deptTime : deptTimeList) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                if (deptTime.isBefore(Ttime_input) && (closestBefore == null || deptTime.isAfter(closestBefore))) {
                                    closestBefore = deptTime;
                                    StrclosestBefore = closestBefore.format(formatTime);
                                }
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                if (deptTime.isAfter(Ttime_input) && (closestAfter == null || deptTime.isBefore(closestAfter))) {
                                    closestAfter = deptTime;
                                    StrclosestAfter = closestAfter.format(formatTime);
                                }
                            }
                        }
                        adapter.filterClosestTimes(StrclosestBefore, StrclosestAfter);
                    }

                } else

                {
                    Toast.makeText(getContext(), "Please ensure the departure and arrival stop are selected and a departure time is filled", Toast.LENGTH_SHORT).show();

                }

                adapter.reset();
            }

        });

        return view;
    }

}

