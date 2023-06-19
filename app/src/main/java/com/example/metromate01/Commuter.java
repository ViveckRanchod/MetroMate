package com.example.metromate01;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Commuter extends AppCompatActivity {
    EditText name,lastname, tagNumber, email,
            password, dob;
    String path ="commuter"; // path set
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_sign_up);
    //assign variables to xml elements:
        signUp = findViewById(R.id.btnsignUp);
        name = findViewById(R.id.editTextTextPersonName);
        lastname= findViewById(R.id.editTextTextPersonName2);
        tagNumber=  findViewById(R.id.editTextTextPersonName4);
        email=  findViewById(R.id.commuterEmail);
        password=  findViewById(R.id.editTextTextPassword);
        dob=  findViewById(R.id.editTextDate);

        //send user input to dbase:
        Database db = new Database();
        //check input conditions:

        signUp.setOnClickListener(sendView -> {
            //get current user inputs:
            String st_name = name.getText().toString();
            String st_lastname = lastname.getText().toString();
            String st_email = email.getText().toString();
            String st_tagNumber = tagNumber.getText().toString();
            String st_password = password.getText().toString();
            String st_dob = dob.getText().toString();

            //set refValues in the same order of the parameters set in signUpToDatabase in getDatabase:
            //refer to Database to see purpose of badgeID = null
            if(!st_name.isEmpty() && !st_lastname.isEmpty() && !st_email.isEmpty() &&!st_password.isEmpty()
                    && st_password.length()>=8 && !st_dob.isEmpty()&& !st_tagNumber.isEmpty()){
                //pass path we want to send data to:
                db.setPath(path);
                db.signUpToDatabase(st_name, st_lastname, st_email, st_password, st_dob, st_tagNumber,
                    null, path);
                Toast.makeText(Commuter.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Commuter.this, "Please ensure are fields are filled & the password is 8 or more characters long", Toast.LENGTH_SHORT).show();
            }
        });
    }
  }
