package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;

interface UserRowView {

    void setUsername(String username);
    void setGravatar(Bitmap image);
    void setBadges(int bronze, int silver, int gold);
}
