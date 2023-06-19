package com.example.metromate01;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // Declare the fragments for the regular user
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private ThirdFragment thirdFragment;
    private FourthFragment fourthFragment;

    // Declare the fragments for the bus driver
    private BusHomeFragment busFragment;
    private BusMapsFragment busFragment2;
    private BusScheduleFragment busFragment3;

    // The flag to indicate if the user is a bus driver or not
    private boolean isBusDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button commuterButton = findViewById(R.id.commuterButton);
        Button driverButton = findViewById(R.id.driverButton);

        commuterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCommuterNavigation();
            }
        });

        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToDriverNavigation();
            }
        });

        // Check if the user is a bus driver or not
        isBusDriver = false; // Default value, you need to implement the logic to set this flag

        // Initialize the fragments for the regular user
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        thirdFragment = new ThirdFragment();
        fourthFragment = new FourthFragment();

        // Initialize the fragments for the bus driver
        busFragment = new BusHomeFragment();
        busFragment2 = new BusMapsFragment();
        busFragment3 = new BusScheduleFragment();

        // Set the initial fragment
        if (isBusDriver) {
            setInitialFragment(busFragment);
        } else {
            setInitialFragment(firstFragment);
        }

        // Set the listener for bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // Inflate the appropriate menu based on the user type
        if (isBusDriver) {
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_driver);
        } else {
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
        }
    }

    private void setInitialFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        // Determine the selected fragment based on the user type
        if (isBusDriver) {
            switch (item.getItemId()) {
                case R.id.busHomePage:
                    selectedFragment = busFragment;
                    break;
                case R.id.busSchedulesPage:
                    selectedFragment = busFragment2;
                    break;
                case R.id.busMapsRoutes:
                    selectedFragment = busFragment3;
                    break;
            }
        } else {
            switch (item.getItemId()) {
                case R.id.homePage:
                    selectedFragment = firstFragment;
                    break;
                case R.id.schedulesPage:
                    selectedFragment = secondFragment;
                    break;
                case R.id.mapsRoutes:
                    selectedFragment = thirdFragment;
                    break;
                case R.id.myTagID:
                    selectedFragment = fourthFragment;
                    break;
            }
        }

        // Replace the fragment if it is not null
        if (selectedFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flFragment, selectedFragment);
            fragmentTransaction.commit();
        }

        return true;
    }

    private void switchToCommuterNavigation() {
        isBusDriver = false;
        setInitialFragment(firstFragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
    }

    private void switchToDriverNavigation() {
        isBusDriver = true;
        setInitialFragment(busFragment);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_driver);
    }
}
