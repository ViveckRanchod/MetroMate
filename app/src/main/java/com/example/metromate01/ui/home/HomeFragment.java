package com.example.metromate01.ui.home;

import android.annotation.SuppressLint;
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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    tripsAdapter tripAdapter;
    ArrayList<trips> list;
    Spinner spinner1, spinner2;
    EditText time;
    Button search;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        recyclerView = root.findViewById(R.id.tripsList);
        database = FirebaseDatabase.getInstance().getReference("trips");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        list = new ArrayList<>();
        ArrayList<trips> filteredList;
        filteredList = new ArrayList<>();
        tripAdapter = new tripsAdapter(requireContext(), list);
        recyclerView.setAdapter(tripAdapter);

        time = root.findViewById(R.id.editTextDepartureTime);
        spinner1 = root.findViewById(R.id.spinner);
        spinner2 = root.findViewById(R.id.spinner2);
        search = root.findViewById(R.id.button);

        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trips trip = dataSnapshot.getValue(trips.class);
                    list.add(trip);
                }
                tripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database cancellation error if needed
            }
        });

        search.setOnClickListener(view -> {
            try {
                String spinner1Selection = spinner1.getSelectedItem().toString();
                String spinner2Selection = spinner2.getSelectedItem().toString();
                String depTime = time.getText().toString();

                if (!spinner1Selection.isEmpty() && !spinner2Selection.isEmpty() && !depTime.isEmpty() && !spinner1Selection.equals(spinner2Selection)) {
                    filteredList.clear();
                    for (trips trip : list) {
                        if (trip.getDepartureStop().equals(spinner1Selection) && trip.getArrivalStop().equals(spinner2Selection)) {
                            filteredList.add(trip);
                        }
                    }

                    if (filteredList.isEmpty()) {
                        Toast.makeText(getContext(), "No available buses for those routes.", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<trips> tripTimes = new ArrayList<>();
                        boolean isInputTimeFound = false;

                        for (trips found : filteredList) {
                            if (found.getDepartureTime().equals(depTime)) {
                                isInputTimeFound = true;
                                tripTimes.add(found);
                                break;
                            }
                        }

                        if (isInputTimeFound) {
                            tripAdapter.update(tripTimes);
                        } else {
                            ArrayList<trips> closestTrips = new ArrayList<>();
                            long minDifference = Long.MAX_VALUE;
                            // Change objects into local time objects:
                            LocalTime timeInput = LocalTime.parse(depTime, DateTimeFormatter.ofPattern("HH:mm"));

                            // Find the closest trips based on the departure time difference:
                            for (trips trip : filteredList) {
                                String deptTimeDB = trip.getDepartureTime();
                                LocalTime timeDB = LocalTime.parse(deptTimeDB, DateTimeFormatter.ofPattern("HH:mm"));

                                long timeDifference = Math.abs(ChronoUnit.MINUTES.between(timeInput, timeDB));

                                if (timeDifference <= minDifference) {
                                    if (timeDifference < minDifference) {
                                        minDifference = timeDifference;
                                        closestTrips.clear();
                                    }
                                    closestTrips.add(trip);
                                }
                            }

                            if (!closestTrips.isEmpty()) {
                                ArrayList<trips> tripTimesFormatted = new ArrayList<>();
                                for (trips getStr : closestTrips) {
                                    String departureTimeFormatted = getStr.getDepartureTime();
                                    getStr.setDepartureTime(departureTimeFormatted);
                                    tripTimesFormatted.add(getStr);
                                }
                                tripAdapter.update(tripTimesFormatted);
                            } else {
                            Toast.makeText(getContext(), "No available buses for those routes.", Toast.LENGTH_SHORT).show();
                        }

                            }


                    }
                } else {
                    Toast.makeText(getContext(), "Please ensure the departure and arrival stop are selected, and a departure time is filled.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
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
