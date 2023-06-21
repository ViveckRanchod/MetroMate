package com.example.metromate01;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Schedule extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner schedule_droplist;
    ImageView schedule_img = findViewById(R.id.schedule_img);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_page);
        //initialise spinner and adapter:
        schedule_droplist = findViewById(R.id.schedule_droplist);
        ArrayAdapter<CharSequence> adaptArr = ArrayAdapter.createFromResource(Schedule.this, R.array.areas_array, android.R.layout.simple_spinner_item);
        adaptArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedule_droplist.setAdapter(adaptArr);
        schedule_droplist.setSelection(0);  //set on display
        schedule_droplist.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int schedule_selected_id = schedule_droplist.getSelectedItemPosition();
        switch(schedule_selected_id){
            case 0:
                    schedule_img.setImageResource(R.drawable.bus_background);
                 case 1:
                         schedule_img.setImageResource(R.drawable.oakdene_schedule);
                         break;
                        case 2:
                               schedule_img.setImageResource(R.drawable.bedford_schedule);
                               break;
                               default:
                                       Toast.makeText(Schedule.this, "Select an schedule to view from the list", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        schedule_img.setImageResource(R.drawable.bus_background);
    }
}