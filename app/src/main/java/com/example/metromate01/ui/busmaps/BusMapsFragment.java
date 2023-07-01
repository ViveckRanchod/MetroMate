package com.example.metromate01.ui.busmaps;



import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.metromate01.R;
import com.example.metromate01.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BusMapsFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapsBinding binding;
    private GoogleMap myMap;
    private SearchView searchView;

    private List<BusStop> busStops;
    private List<busTracking> busTrackings;

    private Handler handler;
    private Runnable updateMarkersRunnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        searchView = view.findViewById(R.id.search_view);

        busStops = new ArrayList<>();
        busTrackings = new ArrayList<>();

        handler = new Handler();
        updateMarkersRunnable = new Runnable() {
            @Override
            public void run() {
                fetchUpdatedBusLocations();
                handler.postDelayed(this, 1000);
            }
        };

        // Retrieve bus stops from Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference stopsRef = database.getReference("stops");

        stopsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()) {
                    String title = stopSnapshot.getKey();
                    double latitude = stopSnapshot.child("latitude").getValue(Double.class);
                    double longitude = stopSnapshot.child("longitude").getValue(Double.class);

                    busStops.add(new BusStop(title, new LatLng(latitude, longitude)));
                }

                if (myMap != null) {
                    onMapReady(myMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchUpdatedBusLocations() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference trackingRef = database.getReference("driverLocations");

        trackingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myMap.clear();

                for (DataSnapshot stopSnapshot : dataSnapshot.getChildren()) {
                    String title = stopSnapshot.getKey();
                    double trackinglatitude = stopSnapshot.child("latitude").getValue(Double.class);
                    double trackinglongitude = stopSnapshot.child("longitude").getValue(Double.class);

                    busTrackings.add(new busTracking(title, new LatLng(trackinglatitude, trackinglongitude)));

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(trackinglatitude, trackinglongitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_tracker_icon_foreground));
                    myMap.addMarker(markerOptions);
                }

                // Re-add the bus stops to the map
                for (BusStop busStop : busStops) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(busStop.getLocation())
                            .title(busStop.getTitle())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pin_foreground));
                    myMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;

        for (BusStop busStop : busStops) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(busStop.getLocation())
                    .title(busStop.getTitle())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pin_foreground));
            myMap.addMarker(markerOptions);
        }

        if (!busStops.isEmpty()) {
            myMap.moveCamera(CameraUpdateFactory.newLatLng(busStops.get(0).getLocation()));
        }

        handler.postDelayed(updateMarkersRunnable, 1000)
        ;
    }
    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(requireContext());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(location, 1);

            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

//                myMap.clear();

                // Add bus stops to the map
                for (BusStop busStop : busStops) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(busStop.getLocation())
                            .title(busStop.getTitle())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pin_foreground));
                    myMap.addMarker(markerOptions);
                }

                // Add tracking markers to the map
                for (busTracking Bustracking : busTrackings) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(Bustracking.getBusLocation())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_tracker_icon_foreground));
                    myMap.addMarker(markerOptions);
                }

                LatLng searchLocation = new LatLng(latitude, longitude);

                // Add the search marker with the default marker icon
                MarkerOptions searchMarkerOptions = new MarkerOptions()
                        .position(searchLocation)
                        .title("Searched Location")
                        .icon(BitmapDescriptorFactory.defaultMarker());
                myMap.addMarker(searchMarkerOptions);

                // Move camera to the searched location
                myMap.moveCamera(CameraUpdateFactory.newLatLng(searchLocation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static class BusStop {
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

    private static class busTracking {
        private LatLng busLocation;

        public busTracking(String title, LatLng busLocation) {
            this.busLocation = busLocation;
        }

        public LatLng getBusLocation() {
            return busLocation;
        }
    }
}

