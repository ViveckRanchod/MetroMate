package com.example.metromate01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserProfileDriverActivity  extends AppCompatActivity {
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_commuter);

        logoutButton = findViewById(R.id.btnLogOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the user session and redirect to the LoginActivity

                // Clear the user session
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                // Redirect to the LoginActivity
                Intent intent = new Intent(UserProfileDriverActivity.this, LandingLaunch.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity to remove it from the back stack
            }
        });
    }
}