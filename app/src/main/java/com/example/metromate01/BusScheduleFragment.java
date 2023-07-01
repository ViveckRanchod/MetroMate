package com.example.metromate01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import java.io.*;

public class BusScheduleFragment extends Fragment {

    public BusScheduleFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<<< HEAD:app/src/main/java/com/example/metromate01/BusScheduleFragment.java

        return inflater.inflate(R.layout.fragment_bus_schedule, container, false);
========
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_my_tag_page, container, false);
>>>>>>>> a001bb024698269bbca25762998fe685fab252db:app/src/main/java/com/example/metromate01/FourthFragment.java
    }
}
