package com.example.chris.androidcodingchallenge;



import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

public class RemoteDataSourceImpl implements RemoteDataSource{

    @Override
    public List<User> createUserList() throws IOException {
        String response = getResponse("https://api.stackexchange.com/2.2/users?site=stackoverflow&filter=!40DELOJ-U(i)MAuGy");
        return parseResponse(response);
    }

    private String getResponse(String urlName)throws IOException{
        URL url = new URL(urlName);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }
        InputStream inputStream = conn.getInputStream();
        if ("gzip".equals(conn.getContentEncoding())) {
            // Is compressed using GZip: Wrap the reader
            inputStream = new GZIPInputStream(inputStream);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        bufferedReader.close();
        conn.disconnect();

        return builder.toString();
    }

    private List<User> parseResponse(String response){
        List<User> items = new ArrayList<>();

        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        JsonArray users = jsonObject.getAsJsonArray("items");
        for(JsonElement element : users) {
            JsonObject user = element.getAsJsonObject();
            String gravatarUrl = user.get("profile_image").getAsString();
            String username = user.get("display_name").getAsString();
            JsonObject badges = user.get("badge_counts").getAsJsonObject();
            int bronze = badges.get("bronze").getAsInt();
            int silver = badges.get("silver").getAsInt();
            int gold = badges.get("gold").getAsInt();

            items.add(new User(gravatarUrl, username, bronze, silver, gold));

        }
        return items;
    }
}
