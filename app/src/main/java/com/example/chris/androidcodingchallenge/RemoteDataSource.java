package com.example.chris.androidcodingchallenge;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface RemoteDataSource {

    List<User> createUserList() throws IOException;

}
