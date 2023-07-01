package com.example.metromate01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import java.io.*;

public class BusHomeFragment extends Fragment {

    public BusHomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

<<<<<<<< HEAD:app/src/main/java/com/example/metromate01/BusHomeFragment.java
        return inflater.inflate(R.layout.driver_homepage, container, false);
========
        return inflater.inflate(R.layout.activity_schedule_page, container, false);
>>>>>>>> a001bb024698269bbca25762998fe685fab252db:app/src/main/java/com/example/metromate01/SecondFragment.java
    }
}
