package com.example.chris.androidcodingchallenge;


public interface MainPresenter {


    void onResume();

    void onBindUserAtPosition(int position, UserAdapter.UserViewHolder holder);

    int getUserCount();

    UserAdapter getAdapter();

}