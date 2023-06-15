package com.ai.educationapp.ui.videogallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VideoGalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VideoGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}