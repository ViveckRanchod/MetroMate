package com.example.metromate01.ui.home;


import android.os.Build;
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
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView recyclerView;
    DatabaseReference database;
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get current values and inputs
                String Sspinner1 = spinner1.getSelectedItem().toString();
                String Sspinner2 = spinner2.getSelectedItem().toString();
                int spinner1_id = spinner1.getSelectedItemPosition();
                int spinner2_id = spinner2.getSelectedItemPosition();
                String Stime = time.getText().toString();

                // Declare objects
                String StrclosestBefore = null;
                String StrclosestAfter = null;
                List<LocalTime> deptTimeList = new ArrayList<>();
                ArrayList<trips> list = new ArrayList<>();

                tripsAdapter adapter = new tripsAdapter(getContext(), list);
                Database db = new Database();

                List<String> deptStopList = db.getChildren("trips", "depatureStop");

                if (!Sspinner1.isEmpty() && !Sspinner2.isEmpty() && !Stime.isEmpty() && spinner1_id != spinner2_id) {
                    if (deptStopList.contains(Stime)) {
                        adapter.filterInputTime(Stime);
                    } else {
                        // Convert time input into local time object
                        DateTimeFormatter format_time = DateTimeFormatter.ofPattern("HH:mm");
                        LocalTime Ttime_input = LocalTime.parse(Stime, format_time);

                        // Convert db list of departure times from db to local time objects
                        for (String timeDB : deptStopList) {
                            LocalTime timeFromDb = LocalTime.parse(timeDB, DateTimeFormatter.ofPattern("HH:mm"));
                            deptTimeList.add(timeFromDb);
                        }

                        // Find closest departure times to user input
                        LocalTime closestBefore = null;
                        LocalTime closestAfter = null;
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
                            Toast.makeText(getContext(), "No avaialble Buses at this time", Toast.LENGTH_SHORT).show();
                        }
                        if (closestAfter != null) {
                            StrclosestAfter = closestAfter.format(formatTime);
                        }else{
                            Toast.makeText(getContext(), "No avaialble Buses at this time", Toast.LENGTH_SHORT).show();
                        }

                        adapter.filterClosestTimes(StrclosestBefore, StrclosestAfter);
                    }
                }
                else {
                    Toast.makeText(getContext(), "Please ensure the departure and arrival stop are selected and a departure time is filled", Toast.LENGTH_SHORT).show();
                }
              //  adapter.reset();
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
