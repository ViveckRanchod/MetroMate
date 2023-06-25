package com.example.metromate01;

import android.content.Intent;
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
        setContentView(R.layout.landing_page);
        // Find the commuter button and set its OnClickListener
        commuterButton = findViewById(R.id.commuter_button);
        commuterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the commuter_sign_up.xml layout
                Intent intent = new Intent(LandingLaunch.this, Commuter.class);
                startActivity(intent);
            }
        });

        // Find the driver button and set its OnClickListener
        driverButton = findViewById(R.id.driver_button);
        driverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the activity_driver_registration.xml page
                Intent intent = new Intent(LandingLaunch.this, Driver.class);
                startActivity(intent);
            }
        });


    }

}