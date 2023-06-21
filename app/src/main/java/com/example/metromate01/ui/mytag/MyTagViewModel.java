package com.example.metromate01.ui.mytag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTagViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyTagViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("myTag");
    }

    public LiveData<String> getText() {
        return mText;
    }
}