package com.example.metromate01.ui.home;

import android.annotation.SuppressLint;
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
    tripsAdapter tripAdapter;
    ArrayList<trips> list;
    Spinner spinner1, spinner2;
    EditText time;
    Button search;

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
            String spinner1Selection = spinner1.getSelectedItem().toString();
            String spinner2Selection = spinner2.getSelectedItem().toString();
            int spinner1Position = spinner1.getSelectedItemPosition();
            int spinner2Position = spinner2.getSelectedItemPosition();
            String departureTime = time.getText().toString();

            if (!spinner1Selection.isEmpty() && !spinner2Selection.isEmpty() && !departureTime.isEmpty() && spinner1Position != spinner2Position) {
                ArrayList<trips> filteredList = new ArrayList<>();

                for (trips trip : list) {
                    String departureStop = db.getChildren("trips", "departureStop").get(list.indexOf(trip));
                    String arrivalStop = db.getChildren("trips", "arrivalStop").get(list.indexOf(trip));
                    if (departureStop.equals(spinner1Selection) && arrivalStop.equals(spinner2Selection)) {
                        filteredList.add(trip);
                    }
                }

                // Sort the list according to time values (if needed)

                if (!filteredList.isEmpty()) {
                    ArrayList<trips> closestTripTimes = new ArrayList<>();

                    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");
                    LocalTime timeInput = LocalTime.parse(departureTime, formatTime);

                    for (trips trip : filteredList) {
                        String availableTime = trip.getDepartureTime();
                        if (availableTime.equals(departureTime)) {
                            closestTripTimes.add(trip);
                        }

                    }

                    long minDifference = Long.MAX_VALUE;

                    for (trips trip : filteredList) {
                        String deptTimeDB = trip.getDepartureTime();
                        LocalTime timeDB = LocalTime.parse(deptTimeDB, formatTime);
                        long timeDifference = Math.abs(ChronoUnit.MINUTES.between(timeInput, timeDB));

                        if (timeDifference < minDifference) {
                            closestTripTimes.clear();
                            closestTripTimes.add(trip);
                            minDifference = timeDifference;
                        } else if (timeDifference == minDifference) {
                            closestTripTimes.add(trip);
                        }
                    }

                    if (closestTripTimes.isEmpty()) {
                        Toast.makeText(getContext(), "No available buses at this time.", Toast.LENGTH_SHORT).show();
                    } else {
                        for (trips trip : closestTripTimes) {
                            int position = filteredList.indexOf(trip);
                            tripsAdapter.MyViewHolder holder = (tripsAdapter.MyViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
                            if (holder != null) {
                                holder.arvTime.setText(trip.getArrivalTime());
                                holder.depTime.setText(trip.getDepartureTime());
                                holder.cashPrice.setText(trip.getCashPrice());
                                holder.tagPrice.setText(trip.getTagPrice());
                                holder.busNo.setText(trip.getBusNo());
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(getContext(), "Please ensure the departure and arrival stop are selected, and a departure time is filled.", Toast.LENGTH_SHORT).show();
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
