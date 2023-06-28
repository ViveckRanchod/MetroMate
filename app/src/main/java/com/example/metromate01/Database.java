package com.example.metromate01;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Database {
    //reference database:
    FirebaseDatabase metromDB; // our db/
    DatabaseReference refPath; // our path/branch/schema

    public void setPath(String path) {
        //set database name:
        metromDB = FirebaseDatabase.getInstance();
        //set reference variable for path:
        refPath = metromDB.getReference(path);
    }

    public void signUpToDatabase(String name, String lastname, String email,
                                 String password, Date dateOfBirth, String badgeID,
                                 String tagNumber,
                                 String path) {
        //convert dateOfBirth to timestamp value:
        long ts_dateOfBirth = dateOfBirth.getTime();

        //store values in hash map:
        HashMap<String, Object> userData = new HashMap<>();
        if (path.equals("driver")) {
            userData.put("badgeID", badgeID);
        } else if (path.equals("commuter")) {
            userData.put("tagNumber", tagNumber);
        }
        userData.put("dateOfBirth", ts_dateOfBirth);
        userData.put("email", email);
        userData.put("lastname", lastname);
        userData.put("name", name);
        userData.put("password", password);
        //send data to path to add user:
        refPath.setValue(userData);
    }


    public void sendReport(String uid, String name, String lastname,
                           String email, String bus_number, String schedule,
                           String date, String message) {
        // set new report unique id
        DatabaseReference newReport = refPath.push();
        // send data to the db:
        HashMap<String, Object> reportData = new HashMap<>();
        reportData.put("uid", uid);
        reportData.put("name", name);
        reportData.put("lastname", lastname);
        reportData.put("email", email);
        reportData.put("bus_number", bus_number);
        reportData.put("schedule", schedule);
        reportData.put("date", date);
        reportData.put("message", message);
        newReport.setValue(reportData);
    }

    //get specific values from the db and branches into a list:
    public ArrayList<String> getChildren(String child, String children) {
        DatabaseReference dbPath = FirebaseDatabase.getInstance().getReference(child);
        ArrayList<String> list = new ArrayList<>();
        dbPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear() ;
                //get specific value for all the children under a main child branch & add to list:
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    String depature_time = shot.child(children).getValue(String.class);
                    list.add(depature_time);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return list;
    }

    //check if the user selections match any of the values in the db branches
   /* public boolean searchDatabaseChild(String path, List<String> list, String searchItem){
        DatabaseReference dbPath = FirebaseDatabase.getInstance().getReference(path);
        List<String> foundSearch = new ArrayList<>();
        dbPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                List<String> found = new ArrayList<>();
                    for(DataSnapshot snap: dataSnapshot.getChildren()){
                        String branch_value = snap.getValue(String.class);
                            if(list.contains(branch_value)){
                                foundSearch.add(branch_value);
                            }
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return !foundSearch.isEmpty(); // return true if values are found
    }*/
}

