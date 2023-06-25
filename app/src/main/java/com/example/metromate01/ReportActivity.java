package com.example.metromate01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class ReportActivity extends AppCompatActivity {

    EditText  message, schedule,dateOfMsg, firstName, lastName, email, bus_number;
    String uid;
    Button report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        firstName = findViewById(R.id.editTextText);
        lastName = findViewById(R.id.editTextText2);
        email = findViewById(R.id.editTextTextEmailAddress);
        schedule = findViewById(R.id.editTextText3);
        dateOfMsg = findViewById(R.id.editTextDate);
        bus_number = findViewById(R.id.editTextNumber);
        report = findViewById(R.id.button3);

        report.setOnClickListener(rView -> {
            //get the current user ID from the firebase database
            FirebaseUser current = FirebaseAuth.getInstance().getCurrentUser();
            if(current!=null){ uid = current.getUid();}

            //get current input:
            String sFirstName = firstName.getText().toString();
            String sLastName = lastName.getText().toString();
            String sEmail = email.getText().toString();
            String sSchedule = schedule.getText().toString();
            String sMessage = message.getText().toString();
            String sBus_number = bus_number.getText().toString();
            String sDate = dateOfMsg.getText().toString();

            if(!sFirstName.isEmpty()&&!sLastName.isEmpty()&&!sEmail.isEmpty()
            &&!sSchedule.isEmpty()&&!sMessage.isEmpty()&&!sBus_number.isEmpty()){
                Database db = new Database();
                db.setPath("report");
                db.sendReport(uid,sFirstName,sLastName,sEmail,sBus_number,sSchedule,sDate, sMessage);
            }
        });
    }
}