package com.example.metromate01.ui.maps;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.metromate01.PlacesAutoCompleteAdapter;
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

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapsBinding binding;
    private GoogleMap myMap;
    private AutoCompleteTextView autoCompleteTextView;

    private List<BusStop> busStops;

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

        autoCompleteTextView = view.findViewById(R.id.search_view);
        autoCompleteTextView.setAdapter(new PlacesAutoCompleteAdapter(requireContext())); // Set the adapter for autocomplete suggestions

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getItemAtPosition(position);
                searchLocation(query);
            }
        });
;

        busStops = new ArrayList<>();

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

                    // Create BusStop object and add it to the list
                    busStops.add(new BusStop(title, new LatLng(latitude, longitude)));
                }

                // Call onMapReady() explicitly to ensure the map is initialized after retrieving data
                if (myMap != null) {
                    onMapReady(myMap);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

                myMap.clear();

                for (BusStop busStop : busStops) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(busStop.getLocation())
                            .title(busStop.getTitle())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_pin_foreground));
                    myMap.addMarker(markerOptions);
                }

                LatLng searchLocation = new LatLng(latitude, longitude);
                myMap.addMarker(new MarkerOptions().position(searchLocation).title("Searched Location"));
                myMap.moveCamera(CameraUpdateFactory.newLatLng(searchLocation));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
