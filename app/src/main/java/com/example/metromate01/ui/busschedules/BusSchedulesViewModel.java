package com.example.metromate01.ui.busschedules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusSchedulesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public BusSchedulesViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("My Trip");
    }


    public LiveData<String> getText() {
        return mText;
    }
}


