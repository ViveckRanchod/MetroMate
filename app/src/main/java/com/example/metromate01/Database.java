package com.example.metromate01;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Date;
import java.util.HashMap;


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

    //get specific value from the database sub branches into a list://
  /*public ArrayList<String> getChildren(String child, String children) {
        DatabaseReference dbPath = FirebaseDatabase.getInstance().getReference(child);
        ArrayList<String> listArr = new ArrayList<>();
        dbPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listArr.clear() ;
                //get specific value for all the children under a main child branch & add to list:
                for (DataSnapshot childBranch : dataSnapshot.getChildren()) {
                    String getChild = childBranch.child(children).getValue(String.class);
                    listArr.add(getChild);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return listArr;
    }*/

}

