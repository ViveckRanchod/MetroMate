package com.example.metromate01;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.SupportMapFragment;

public class mapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); // Replace with your layout file name

        // Retrieve the map container and add the SupportMapFragment
        SupportMapFragment mapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapContainer, mapFragment)
                .commit();
    }
}
