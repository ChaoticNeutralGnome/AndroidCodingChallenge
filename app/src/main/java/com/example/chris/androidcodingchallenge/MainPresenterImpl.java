package com.example.chris.androidcodingchallenge;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {

    private MainView mainView;
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private UserAdapter userAdapter;

    private List<User> users;

    public MainPresenterImpl(MainView mainView, RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.mainView = mainView;
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
        this.users = new ArrayList<>();
        this.userAdapter = new UserAdapter(this);
    }

    @Override
    public void onResume() {
        new FetchUsers(remoteDataSource).execute();
    }


    private void setUsers(List<User> users) {
        this.users = users;
        userAdapter.notifyDataSetChanged();
        for(int i=0; i<users.size(); i++){
            User user = users.get(i);
            new FetchImage(user.gravatarUrl, i, remoteDataSource, localDataSource).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    private void setImage(Bitmap image, int index){
        users.get(index).gravatar = image;
        userAdapter.notifyItemChanged(index);
    }

    @Override
    public void onBindUserAtPosition(int position, UserAdapter.UserViewHolder holder) {
        User user = users.get(position);
        holder.setGravatar(user.gravatar);
        holder.setUsername(user.username);
        holder.setBadges(user.bronze, user.silver, user.gold);
    }

    @Override
    public int getUserCount() {
        return users.size();
    }

    @Override
    public UserAdapter getAdapter() {
        return userAdapter;
    }


    private class FetchUsers extends AsyncTask<Void, Void, Void> {

        private String exception;
        private List<User> value;
        private RemoteDataSource rds;

        public FetchUsers(RemoteDataSource rds){
            super();
            this.rds = rds;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                this.value = rds.createUserList();
            }
            catch(IOException e){
                this.exception = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(exception!=null){
                mainView.logException(exception);
            }
            else {
                setUsers(value);
            }
        }
    }

    private class FetchImage extends AsyncTask<Void, Void, Void> {

        private String exception;
        private RemoteDataSource rds;
        private LocalDataSource lds;
        private int index;
        private Bitmap image;
        private String url;


        public FetchImage(String url, int index, RemoteDataSource rds, LocalDataSource lds){
            super();
            this.url = url;
            this.index = index;
            this.rds = rds;
            this.lds = lds;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                this.image = lds.getImage(url);
                if(image==null) {
                    System.out.println(url + " not in database");
                    this.image = rds.getBitmapFromUrl(url);
                    lds.addImage(url, image);
                }
            }
            catch(IOException e){
                this.exception = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(exception!=null){
                mainView.logException(exception);
            }
            else {
                setImage(image, index);
            }
        }
    }
}

