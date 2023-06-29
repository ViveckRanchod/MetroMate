package com.example.metromate01.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metromate01.Database;
import com.example.metromate01.R;
import com.example.metromate01.databinding.FragmentHomeBinding;
import com.example.metromate01.trips;
import com.example.metromate01.tripsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView recyclerView;
    DatabaseReference database;
    Database db = new Database();
    tripsAdapter TripAdapter;
    ArrayList<trips> list;
    Spinner spinner1, spinner2;
    EditText time;
    Button search;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        recyclerView = root.findViewById(R.id.tripsList); // Use root.findViewById instead of recyclerView.findViewById
        database = FirebaseDatabase.getInstance().getReference("trips");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext())); // Use requireContext() instead of this

        list = new ArrayList<>();
        TripAdapter = new tripsAdapter(requireContext(), list); // Use requireContext() instead of this
        recyclerView.setAdapter(TripAdapter);

        //Viveck- changed it to inflate root
        View view = root;

        time = view.findViewById(R.id.editTextDepartureTime);
        spinner1 = view.findViewById(R.id.spinner);
        spinner2 = view.findViewById(R.id.spinner2);
        search = view.findViewById(R.id.button);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new items

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trips Trip = dataSnapshot.getValue(trips.class);
                    list.add(Trip);
                }

                TripAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search.setOnClickListener(view1 -> {
            // Get current values and inputs
            String Sspinner1 = spinner1.getSelectedItem().toString();
            String Sspinner2 = spinner2.getSelectedItem().toString();
            int spinner1_id = spinner1.getSelectedItemPosition();
            int spinner2_id = spinner2.getSelectedItemPosition();
            String Stime = time.getText().toString();


            if (!Sspinner1.isEmpty() && !Sspinner2.isEmpty() && !Stime.isEmpty() && spinner1_id != spinner2_id) {
                ArrayList<trips> filterList = new ArrayList<>();

                //check that the selections are in the database and add the selections to the 	filterlist

                for (trips destination : list) {
                    String firstSelection = db.getChildren("trips", "departureStop").get(list.indexOf(destination));
                    String secondSelection = db.getChildren("trips", "arrivalStop").get(list.indexOf(destination));
                    if (firstSelection.equals(Sspinner1) && secondSelection.equals(Sspinner2)) {
                        filterList.add(destination);
                    }
                }
                //sort list according to time values:
                // if (deptStopList.contains(Stime){TripAdapter.filterInputTime(Stime);}

                if (!filterList.isEmpty()) {
                    ArrayList<trips> closestTripTimes = new ArrayList<>();

                    // Convert time input into local time object
                    DateTimeFormatter format_time = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        format_time = DateTimeFormatter.ofPattern("HH:mm");
                    }
                    LocalTime Ttime_input = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Ttime_input = LocalTime.parse(Stime, format_time);
                    }

                    for (trips time : filterList) {
                        String availableTime = time.getDepartureTime();
                        if(availableTime.equals(Stime)){
                            closestTripTimes.add(time);
                        }
                    }
                    //get closest times to the input by calculating minutes between existing departureTime values :
                    long minDifference = Long.MAX_VALUE;

                    for (trips time : filterList) {
                        String deptTimeDB = time.getDepartureTime();
                        LocalTime TIME_deptTimeDB = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            TIME_deptTimeDB = LocalTime.parse(deptTimeDB, format_time);
                        }
                        long timeDifference = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            timeDifference = Math.abs(ChronoUnit.MINUTES.between(Ttime_input, TIME_deptTimeDB));
                        }

                        if (timeDifference < minDifference) {
                            closestTripTimes.clear();
                            closestTripTimes.add(time);
                            minDifference = timeDifference;
                        } else if (minDifference == timeDifference) {
                            closestTripTimes.add(time);//
                        }
                    }
                    if (closestTripTimes.isEmpty()) {
                        Toast.makeText(getContext(), "No available buses at this time.", Toast.LENGTH_SHORT).show();
                    } else {
                        for (trips Trips : closestTripTimes) {

                            // Get the position of the current trip in the filterlist
                            int position = closestTripTimes.indexOf(Trips);

                            tripsAdapter.MyViewHolder holder = (tripsAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                            // fetch the corresponding trip from the list based on the position
                            trips Trip = filterList.get(position);
                            if(holder!=null) {
                                holder.arvTime.setText(Trip.getArrivalTime());
                                holder.depTime.setText(Trip.getDepartureTime());
                                holder.cashPrice.setText(Trip.getCashPrice());
                                holder.tagPrice.setText(Trip.getTagPrice());
                                holder.busNo.setText(Trip.getBusNo());
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(getContext(), "Please ensure the departure and arrival stop are selected and a departure time is filled", Toast.LENGTH_SHORT).show();
            }
        });

return root;

        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            binding = null;
        }
    }
    /*/ Convert db list of departure times from db to local time objects
                    for (String timeDB : deptStopList) {
                        LocalTime timeFromDb = LocalTime.parse(timeDB, DateTimeFormatter.ofPattern("HH:mm"));
                        deptTimeList.add(timeFromDb);
                    }

                    // Find closest departure times to user input
                    for (LocalTime deptTime : deptTimeList) {
                        if (deptTime.isBefore(Ttime_input) && (closestBefore == null || deptTime.isAfter(closestBefore))) {
                            closestBefore = deptTime;
                        }
                        if (deptTime.isAfter(Ttime_input) && (closestAfter == null || deptTime.isBefore(closestAfter))) {
                            closestAfter = deptTime;
                        }
                    }

                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
                    if (closestBefore != null) {
                        StrclosestBefore = closestBefore.format(formatTime);
                    }else{
                        Toast.makeText(getContext(), "No avaialble Bus at this time", Toast.LENGTH_SHORT).show();
                    }
                    if (closestAfter != null) {
                        StrclosestAfter = closestAfter.format(formatTime);
                    }else{
                        Toast.makeText(getContext(), "No avaialble Bus at this time", Toast.LENGTH_SHORT).show();
                    }*/
