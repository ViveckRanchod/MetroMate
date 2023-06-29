package com.example.metromate01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Driver extends AppCompatActivity {

    EditText name, lastname, badgeID, email, password, dob;
    String path = "driver";
    int tagNumber = 0;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_page);

        signUp = findViewById(R.id.btnSignUp);
        name = findViewById(R.id.edtName);
        lastname = findViewById(R.id.edtLastName);
        badgeID = findViewById(R.id.edtBadgeId);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        dob = findViewById(R.id.edtDateOfBirth);

        signUp.setOnClickListener(view -> {
            String st_name = name.getText().toString();
            String st_lastname = lastname.getText().toString();
            String st_email = email.getText().toString();
            int in_badgeID = Integer.parseInt(badgeID.getText().toString());
            String st_password = password.getText().toString();
            String st_dob = dob.getText().toString();

            if (!st_name.isEmpty() && !st_lastname.isEmpty() && !st_email.isEmpty() && !st_password.isEmpty()
                    && st_password.length() >= 8 && !st_dob.isEmpty() && in_badgeID > 0) {
                Database db = new Database();
                db.setPath(path);
                db.signUpToDatabase(st_name, st_lastname, st_email, st_password, st_dob, in_badgeID, tagNumber, path);
                Toast.makeText(Driver.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                Intent intent = new Intent(Driver.this, BusDriverActivity.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity to prevent going back to the registration page
            } else {
                Toast.makeText(Driver.this, "Please ensure all fields are filled and the password is 8 or more characters long", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
