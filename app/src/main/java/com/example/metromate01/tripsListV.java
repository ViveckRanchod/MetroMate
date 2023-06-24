package com.example.metromate01;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class tripsListV extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    tripsAdapterV tripsAdapter;
    HashMap<String, viveckTrips> tripsMap; // Updated to HashMap

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_trip);

        recyclerView = findViewById(R.id.homeRecycleView);
        database = FirebaseDatabase.getInstance().getReference("trips");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tripsMap = new HashMap<>(); // Initialize the HashMap

        tripsAdapter = new tripsAdapterV(this, tripsMap);
        recyclerView.setAdapter(tripsAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tripsMap.clear(); // Clear the map before adding new data

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String key = childSnapshot.getKey(); // Get the key (e.g., "eduvos_gandhi")

                    // Iterate through the child nodes of the key
                    for (DataSnapshot tripSnapshot : childSnapshot.getChildren()) {
                        String tripKey = tripSnapshot.getKey(); // Get the key (e.g., "time", "price")
                        viveckTrips trip = tripSnapshot.getValue(viveckTrips.class);

                        // Add the trip to the map using the combination of key and tripKey
                        tripsMap.put(key + "_" + tripKey, trip);
                    }
                }

                tripsAdapter.notifyDataSetChanged(); // Notify the adapter about the data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            // ...
        });

    }
}
