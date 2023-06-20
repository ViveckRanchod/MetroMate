package com.example.metromate01;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.appcompat.widget.SearchView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.android.gms.maps.SupportMapFragment;


public class Map extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private SearchView searchView;
    private List<BusStop> busStops;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Initializes the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        // Initialize the search view

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform the search when the user submits the query
                searchLocation(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle any text changes in the search view
                return false;
            }
        });

        // Initialize bus stops
        busStops = new ArrayList<>();
        busStops.add(new BusStop("Ghandi Square", new LatLng(-26.207079851461764, 28.043346217111473)));
        busStops.add(new BusStop("Bedford Plaza", new LatLng(-26.177853926086073, 28.134863838086332)));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        // Add bus stop markers
        for (BusStop busStop : busStops) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(busStop.getLocation())
                    .title(busStop.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pin_icon_foreground));
            myMap.addMarker(markerOptions);
        }

        // Move the camera to the first bus stop
        if (!busStops.isEmpty()) {
            myMap.moveCamera(CameraUpdateFactory.newLatLng(busStops.get(0).getLocation()));
        }
    }

    private void searchLocation(String location) {
        // Perform the search for the given location
        // Here, you can use any geocoding service or library to convert the location string to latitude and longitude coordinates

        // For example, you can use the Geocoder class from the Android framework:
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            // Perform geocoding for the location query
            addresses = geocoder.getFromLocationName(location, 1);

            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

                // Clear existing markers
                myMap.clear();

                // Add bus stop markers
                for (BusStop busStop : busStops) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(busStop.getLocation())
                            .title(busStop.getTitle())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pin_icon_foreground));
                    myMap.addMarker(markerOptions);
                }

                // Add a marker for the searched location
                LatLng searchLocation = new LatLng(latitude, longitude);
                myMap.addMarker(new MarkerOptions().position(searchLocation).title("Searched Location"));

                // Move the camera to the searched location
                myMap.moveCamera(CameraUpdateFactory.newLatLng(searchLocation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class BusStop {
        private String title;
        private LatLng location;

        public BusStop(String title, LatLng location) {
            this.title = title;
            this.location = location;
        }

        public String getTitle() {
            return title;
        }

        public LatLng getLocation() {
            return location;
        }
    }
}
