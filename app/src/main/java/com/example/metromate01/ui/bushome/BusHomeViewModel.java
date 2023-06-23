package com.example.metromate01.ui.bushome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusHomeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public BusHomeViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("My Trip");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
