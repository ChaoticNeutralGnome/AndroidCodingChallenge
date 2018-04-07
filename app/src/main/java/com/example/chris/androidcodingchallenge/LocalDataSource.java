package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;

interface LocalDataSource {

    void addImage(String key, Bitmap value);

    Bitmap getImage(String key);

}
