package com.example.chris.androidcodingchallenge;


import android.graphics.Bitmap;

public class User {

    String gravatarUrl;
    String username;
    int bronze, silver, gold;
    Bitmap gravatar;

    User(String gravatarUrl, String username, int bronze, int silver, int gold){
        this.gravatarUrl = gravatarUrl;
        this.username = username;
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
        this.gravatar = null;
    }

}

