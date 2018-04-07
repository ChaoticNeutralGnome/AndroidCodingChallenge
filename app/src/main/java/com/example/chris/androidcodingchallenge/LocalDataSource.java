package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;

public interface LocalDataSource {

    void addImage(String key, Bitmap value);

    Bitmap getImage(String key);

}
