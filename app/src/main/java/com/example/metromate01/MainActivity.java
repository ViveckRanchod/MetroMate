package com.example.metromate01;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.metromate01.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        // Find the buttons in the landing page
        Button signUpButton1 = findViewById(R.id.driverButton);
        Button signUpButton2 = findViewById(R.id.commuterButton);

        // Set click listeners for the sign-up buttons
        signUpButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the first sign-up activity
                Intent intent = new Intent(MainActivity.this, Driver.class);
                startActivity(intent);
            }
        });

        signUpButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the second sign-up activity
                Intent intent = new Intent(MainActivity.this, Commuter.class);
                startActivity(intent);
            }
        });

//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        switch (item.getItemId()) {
//                            case R.id.navigation_item1:
//                                // Open MainFragment
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.flFragment, new MainFragment())
//                                        .commit();
//                                return true;
//                            case R.id.navigation_item2:
//                                // Open SchedulePageFragment
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.flFragment, new SchedulePageFragment())
//                                        .commit();
//                                return true;
//                            case R.id.navigation_item3:
//                                // Open MapsFragment
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.flFragment, new MapsFragment())
//                                        .commit();
//                                return true;
//                            case R.id.navigation_item4:
//                                // Open MyTagPageFragment
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.flFragment, new MyTagPageFragment())
//                                        .commit();
//                                return true;
//                        }
//                        return false;
//                    }
//                });
    }
}



