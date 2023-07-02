package com.example.metromate01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LandingLaunch extends AppCompatActivity {

    private Button commuterButton;
    private Button driverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // User is already logged in
            boolean isDriver = sharedPreferences.getBoolean("isDriver", false);
            if (isDriver) {
                // Start the BusDriverActivity
                Intent intent = new Intent(LandingLaunch.this, BusDriverActivity.class);
                startActivity(intent);
            } else {
                // Start the MainActivity
                Intent intent = new Intent(LandingLaunch.this, MainActivity.class);
                startActivity(intent);
            }
            finish(); // Finish the LandingLaunch activity to prevent going back to it
        } else {
            // User is not logged in, show the landing page
            setContentView(R.layout.landing_page);

            // Find the commuter button and set its OnClickListener
            Button commuterButton = findViewById(R.id.commuter_button);
            commuterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open the Commuter activity
                    Intent intent = new Intent(LandingLaunch.this, Commuter.class);
                    startActivity(intent);
                }
            });

            // Find the driver button and set its OnClickListener
            Button driverButton = findViewById(R.id.driver_button);
            driverButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open the Driver activity
                    Intent intent = new Intent(LandingLaunch.this, Driver.class);
                    startActivity(intent);
                }
            });
        }
    }
}





