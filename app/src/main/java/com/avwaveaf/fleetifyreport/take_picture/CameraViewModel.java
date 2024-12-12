package com.avwaveaf.fleetifyreport.take_picture;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CameraViewModel extends ViewModel {

    private final MutableLiveData<Uri> capturedImageUri = new MutableLiveData<>();

    public void onImageCaptured(Uri uri) {
        capturedImageUri.postValue(uri);
    }

    public LiveData<Uri> getCapturedImageUri() {
        return capturedImageUri;
    }
}