package com.unipi.p17019p17024.educationalsoftware.ui.myProgress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyProgressViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyProgressViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is myProgress fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}