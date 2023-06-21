package com.example.metromate01;

import android.content.Intent;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);

        // Open DriverRegistrationPageActivity
//        Intent intent = new Intent(this, Driver.class);
//        startActivity(intent);

        // Open commuter_registration activity
//        Intent intentCommuter = new Intent(this, Commuter.class);
//        startActivity(intentCommuter);

        // Open schedule
        Intent intentSchedule = new Intent(this, Schedule.class);
        startActivity(intentSchedule);
    }
}