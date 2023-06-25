package com.example.metromate01;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Commuter extends AppCompatActivity {

    RecyclerView referenceView;
    Database reference;

    ArrayList<Object> areas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commuter_sign_up);

        // Additional setup or logic for the activity
        // ...
    }
   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_bottom_sheet);

                referenceView = findViewById(R.id.tagPrice);
                Database db = new Database();
                db.setNextChild("trips","eduvos_gandhi");
                referenceView.setHasFixedSize(true);
                referenceView.setLayoutManager(new ConstraintLayoutManager(this));

                areas = newArrayList<>;
                mAdapter = new mAdapter(this, areas);
                referenceView.setAdapter(mAdapter);

                reference.addEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot){
                        for(DataSnapshot ds: snapshot.getChildren()){
                            Trips getTripInfo = dataSnapshot.getValue(Trips.class);
                            areas.add(getTripInfo);
                        }
                    };

                });
        }
    */

}
