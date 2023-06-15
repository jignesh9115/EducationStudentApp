package com.ai.educationapp.ui.photogallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PhotoGalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PhotoGalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}