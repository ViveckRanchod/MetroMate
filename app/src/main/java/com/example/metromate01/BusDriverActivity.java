package com.example.metromate01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.metromate01.databinding.ActivityBusDriverBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BusDriverActivity extends AppCompatActivity {
    private ActivityBusDriverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        BottomNavigationView navView = findViewById(R.id.nav_view1);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_bus_home, R.id.navigation_bus_schedules, R.id.navigation_bus_maps)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_bus_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView1, navController);
    }
}
