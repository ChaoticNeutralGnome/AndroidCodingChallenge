package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;

public class LocalDataSourceImpl implements LocalDataSource{

    @Override
    public boolean containsImage(String key) {
        return false;
    }

    @Override
    public void addImage(String key, Bitmap value) {

    }

    @Override
    public Bitmap getImage(String key) {
        return null;
    }
}
