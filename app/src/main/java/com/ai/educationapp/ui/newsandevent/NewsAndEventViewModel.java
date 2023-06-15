package com.ai.educationapp.ui.newsandevent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsAndEventViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewsAndEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}