package com.example.chris.androidcodingchallenge;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;



public class MainActivity extends AppCompatActivity implements MainView {
    RecyclerView rv;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(this, new RemoteDataSourceImpl(), new LocalDataSourceImpl());
        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }



    @Override
    public void logException(String message) {
        messageBox(message);
    }


    private void initializeView(){
        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(mainPresenter.getAdapter());
    }

    private void messageBox(String message)
    {
        Log.d("EXCEPTION: ",  message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle("Exception");
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }
}
