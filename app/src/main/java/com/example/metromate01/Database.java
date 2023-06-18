package com.example.metromate01;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class Database {
    //reference database:
    FirebaseDatabase metromDB; // our db/
    DatabaseReference refPath; // our path/branch/schema

    //method resuable for both commuter and driver:
    public void setPath(String path){
        //set database name:
        metromDB = FirebaseDatabase.getInstance();
        //set reference variable for path:
        refPath = metromDB.getReference(path);
    }

    public void signUpToDatabase(String name, String lastname, String email,
                                 String password, String dateOfBirth,String badgeID,
                                 String tagNumber,
                                 String path)
    {
        //store values in hash map:
        // set badgeID and tagNumber to be null in the opposing classes
        HashMap<String, String> userData = new HashMap<>();
        if(path.equals("driver")){
            userData.put("badgeID", badgeID);
        } else if (path.equals("commuter")){
            userData.put("tagNumber", tagNumber);
        }
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("email", email);
        userData.put("lastname", lastname);
        userData.put("name", name);
        userData.put("password", password);
        //send data to path to add user:
        refPath.setValue(userData);
    }
}
