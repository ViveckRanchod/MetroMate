package com.example.metromate01;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class  Database {
    //reference database:
    FirebaseDatabase metromDB; // our db
    DatabaseReference refPath; // our path/branch

    //method reusable for both commuter and driver:
    public void setPath(String path){
        //set database name:
        metromDB = FirebaseDatabase.getInstance();
        //set reference variable for path:
        refPath = metromDB.getReference(path);
    }

    // set badgeID =0 in commuter class and tagNumber to be =0 in driver class, to execute method:
    public void signUpToDatabase(String name, String lastname, String email,
                                 String password, String dateOfBirth, int badgeID,
                                 int tagNumber,
                                 String path)
    {
        DatabaseReference newUser = refPath.push();

        //store editText values in a hash map:
        HashMap<String, Object> userData = new HashMap<>();
        if(path.equals("driver")){
            userData.put("badgeID", badgeID);
        }
        if (path.equals("commuters")){
            userData.put("tagNumber", tagNumber);
        }
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("email", email);
        userData.put("lastname", lastname);
        userData.put("name", name);
        userData.put("password", password);

        //send data to path to add new user:
        newUser.setValue(userData);
    }

    public void sendReport(String uid, String bus_number, String route,
                           String nextStop, String eventType,
                           String timeEvent, String delay) {
        // set new report unique id
        DatabaseReference newReport = refPath.push();
        // send data to the db:
        HashMap<String, Object> reportData = new HashMap<>();
        reportData.put("uid", uid);
        reportData.put("busNumber", bus_number);
        reportData.put("route", route);
        reportData.put("nextStop", nextStop);
        reportData.put("eventType", eventType);
        reportData.put("timeEvent", timeEvent);
        reportData.put("delay", delay);

        newReport.setValue(reportData);
    }
}