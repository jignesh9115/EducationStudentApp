package com.ai.educationapp.ui.leaveapplication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeaveApplicationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LeaveApplicationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}