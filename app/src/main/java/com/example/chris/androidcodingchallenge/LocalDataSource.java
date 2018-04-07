package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;

public interface LocalDataSource {

    boolean containsImage(String key);

    void addImage(String key, Bitmap value);

    Bitmap getImage(String key);

}
