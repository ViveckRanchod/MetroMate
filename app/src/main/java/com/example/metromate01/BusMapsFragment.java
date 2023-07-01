package com.example.metromate01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import java.io.*;

public class BusMapsFragment extends Fragment {

    public BusMapsFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<<< HEAD:app/src/main/java/com/example/metromate01/BusMapsFragment.java

        return inflater.inflate(R.layout.fragment_bus_maps, container, false);
========
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main, container, false);
>>>>>>>> a001bb024698269bbca25762998fe685fab252db:app/src/main/java/com/example/metromate01/FirstFragment.java
    }
}
