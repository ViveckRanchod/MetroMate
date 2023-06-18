package com.example.metromate01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Driver extends AppCompatActivity {

    EditText name,lastname, badgeID, email,
            password, dob;

    String path ="driver";
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration_page);


    //assign variables to xml elements and convert to string:
        signUp = findViewById(R.id.btnSignUp);

        name = findViewById(R.id.edtName);
        String st_name = name.getText().toString();

        lastname= findViewById(R.id.edtLastName);
        String st_lastname = lastname.getText().toString();

        badgeID=  findViewById(R.id.edtBadgeId);
        String st_badgeID = badgeID.getText().toString();

        email=  findViewById(R.id.edtEmail);
        String st_email = email.getText().toString();

        password=  findViewById(R.id.edtPassword);
        String st_password = password.getText().toString();

        dob=  findViewById(R.id.edtDateOfBirth);
        String st_dob = dob.getText().toString();


        //send user input to dbase:
        Database db = new Database();
        //check input conditions:
        if(!st_name.isEmpty() && !st_lastname.isEmpty() && !st_email.isEmpty() &&!st_password.isEmpty()
                && st_password.length()>=8 && !st_dob.isEmpty()&& st_badgeID.isEmpty()){
        signUp.setOnClickListener(sendView -> {
            //pass path we want to send data to:
            db.setPath(path);
            //set refValues in the same order of the parameters set in signUpToDatabase in getDatabase:
            //refer to Database to see purpose of tagNumber= null
            db.signUpToDatabase(st_name, st_lastname, st_email, st_password, st_dob, st_badgeID,
                    null, path);
        });
        }else {

        }
    }
}

/* name= (EditText) findViewById(R.id.edtName);
        String st_name = name.getText().toString();*/