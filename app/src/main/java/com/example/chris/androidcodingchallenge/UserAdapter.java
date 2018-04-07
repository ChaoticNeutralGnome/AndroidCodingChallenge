package com.example.chris.androidcodingchallenge;


import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private final MainPresenter presenter;

    public UserAdapter(MainPresenter presenter){
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_layout, null);
        return  new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        presenter.onBindUserAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getUserCount();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements UserRowView{
        final ImageView gravatarView;
        final TextView usernameView;
        final TextView bronzeView;
        final TextView silverView;
        final TextView goldView;
        final ProgressBar loadingBar;

        UserViewHolder(View itemView) {
            super(itemView);
            gravatarView = itemView.findViewById(R.id.gravatarView);
            usernameView = itemView.findViewById(R.id.usernameView);
            bronzeView = itemView.findViewById(R.id.bronze_number);
            silverView = itemView.findViewById(R.id.silver_number);
            goldView = itemView.findViewById(R.id.gold_number);
            loadingBar = itemView.findViewById(R.id.loadingBar);
        }


        @Override
        public void setUsername(String username) {
            usernameView.setText(username);
        }

        @Override
        public void setGravatar(Bitmap image) {
            gravatarView.setImageBitmap(image);
            if(image!=null) {
                loadingBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void setBadges(int bronze, int silver, int gold) {
            bronzeView.setText(String.valueOf(bronze));
            silverView.setText(String.valueOf(silver));
            goldView.setText(String.valueOf(gold));
        }
    }
}
