package com.example.metromate01.ui.busmaps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusMapsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public BusMapsViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("My Trip");
    }
    public LiveData<String> getText() {
        return mText;
    }
}