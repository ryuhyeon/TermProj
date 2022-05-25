package com.example.termproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent loading = new Intent(this, LoadingActivity.class);
        startActivity(loading);

        Intent today=new Intent(this,TodayActivity.class);
        startActivity(today);
    }
}