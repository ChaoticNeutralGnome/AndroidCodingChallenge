package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;


import java.io.IOException;
import java.util.List;

public interface RemoteDataSource {

    List<User> createUserList() throws IOException;

    Bitmap getBitmapFromUrl(String urlName) throws IOException;

}
