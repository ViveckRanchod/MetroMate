package com.example.metromate01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Commuter extends AppCompatActivity {
    EditText name, lastname, tagNumber, email,
            password, dob;
    String path = "commuters"; // path set
    int badgeId = 0;
    Button signUp;
    Button logoutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_sign_up);

        //assign variables to xml elements:
        signUp = findViewById(R.id.btnsignUp);
        name = findViewById(R.id.editTextTextPersonName);
        lastname = findViewById(R.id.editTextTextPersonName2);
        tagNumber = findViewById(R.id.editTextTextPersonName4);
        email = findViewById(R.id.commuterEmail);
        password = findViewById(R.id.editTextTextPassword);
        dob = findViewById(R.id.editTextDate);

        //send user input to database:
        Database db = new Database();
        //check input conditions:

        signUp.setOnClickListener(sendView -> {
            //get current user inputs:
            String st_name = name.getText().toString();
            String st_lastname = lastname.getText().toString();
            String st_email = email.getText().toString();
            int in_tagNumber = Integer.parseInt(tagNumber.getText().toString());
            String st_password = password.getText().toString();
            String st_dob = dob.getText().toString();

            //set refValues in the same order of the parameters set in signUpToDatabase in Database class:
            //refer to Database class to see purpose of badgeID = 0

            if (!st_name.isEmpty() && !st_lastname.isEmpty() && !st_email.isEmpty() && !st_password.isEmpty()
                    && st_password.length() >= 8 && !st_dob.isEmpty() && in_tagNumber > 0) {
                //pass path we want to send data to:
                db.setPath(path);
                db.signUpToDatabase(st_name, st_lastname, st_email, st_password, st_dob, badgeId, in_tagNumber, path);
                Toast.makeText(Commuter.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();

                // After successful registration
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", true);
                editor.apply();

                // Start the MainActivity or desired activity
                Intent intent = new Intent(Commuter.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity to remove it from the back stack
            } else {
                Toast.makeText(Commuter.this, "Please ensure all fields are filled & the password is 8 or more characters long", Toast.LENGTH_SHORT).show();
            }
   });
}
}