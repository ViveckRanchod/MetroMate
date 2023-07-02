package com.example.metromate01.ui.bushome;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.metromate01.Database;
import com.example.metromate01.R;
import com.example.metromate01.databinding.FragmentBusHomeBinding;
import com.example.metromate01.ui.bushome.BusHomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.ValueEventListener;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
public class BusHomeFragment extends Fragment {

    private FragmentBusHomeBinding binding;
    private DatabaseReference driverLocationRef;
    private String driverId;
    private boolean isTrackingEnabled;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private EditText bus_number, route, nextStop,timeEvent, eventType, delay;
    private String uid;
    private Button report;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        BusHomeViewModel busHomeViewModel = new ViewModelProvider(this).get(BusHomeViewModel.class);

        binding = FragmentBusHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBusHome;
        busHomeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Generate a unique driver ID (can use UUID or any other method)
        driverId = generateDriverId();

        // Get a reference to the driver location node in Firebase
        driverLocationRef = FirebaseDatabase.getInstance().getReference().child("driverLocations");

        // Set up the switch
        Switch switch1 = binding.switch1;
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isTrackingEnabled = isChecked;

            if (isTrackingEnabled) {
                // Start updating the driver's location when tracking is enabled
                startUpdatingLocation();
            } else {
                // Stop updating the driver's location when tracking is disabled
                stopUpdatingLocation();
            }

            //Report submit function:
            // get views into instances -->
            bus_number = root.findViewById(R.id.editText);
            route = root.findViewById(R.id.editTextTextPersonName5);
            nextStop = root.findViewById(R.id.editTextTextPersonName7);
            eventType = root.findViewById(R.id.eventType);
            timeEvent = root.findViewById(R.id.editTextTime);
            delay = root.findViewById(R.id.eventType2);
            report = root.findViewById(R.id.button4);

            report.setOnClickListener(rView -> {
                //get the current user ID from the firebase database
                FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
                if(current!=null){ uid = current.getUid();}

                //get current input:
                String sBus_number = bus_number.getText().toString();
                String sRoute = route.getText().toString();
                String sNextStop = nextStop.getText().toString();
                String sTimeEvent = timeEvent.getText().toString();
                String sEventType = eventType.getText().toString();
                String sDelay = delay.getText().toString();

                if(sBus_number.isEmpty()&& sRoute.isEmpty()&& sNextStop.isEmpty() &&sEventType.isEmpty()
                        && sTimeEvent.isEmpty()&& sDelay.isEmpty())
                {
                    Toast.makeText(getContext(),"Please ensure all fields are filled and selected", Toast.LENGTH_SHORT).show();

                }else{
                    Database db = new Database();
                    db.setPath("report");
                    db.sendReport(uid, sBus_number, sRoute, sNextStop,
                            sEventType, sTimeEvent, sDelay);
                }
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String generateDriverId() {
        // Implement your logic to generate a unique driver ID
        // For example, you can use UUID.randomUUID().toString()
        return UUID.randomUUID().toString();
    }

    private void startUpdatingLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            // Check if the ACCESS_FINE_LOCATION permission is granted
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission already granted, request location updates
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            } else {
                // Request the permission from the user
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    // Override the onRequestPermissionsResult() method to handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start updating location
                startUpdatingLocation();
            } else {
                // Permission denied, show a message or handle accordingly
                Toast.makeText(getActivity(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Create a LocationListener to receive location updates
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Fetch the device's location here (example coordinates used)
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Store the location in Firebase under driverLocations
            DatabaseReference driverLocationRef = FirebaseDatabase.getInstance().getReference().child("driverLocations");
            DatabaseReference driverRef = driverLocationRef.child(driverId);

            // Check if the driver already exists in the database
            driverRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Update the existing location coordinates
                        driverRef.child("latitude").setValue(latitude);
                        driverRef.child("longitude").setValue(longitude);
                    } else {
                        // Create a new location entry in the database
                        driverRef.child("latitude").setValue(latitude);
                        driverRef.child("longitude").setValue(longitude);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error if necessary
                }
            });
        }

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) {


        }
    };

    private void stopUpdatingLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        isTrackingEnabled = false;
    }
}
