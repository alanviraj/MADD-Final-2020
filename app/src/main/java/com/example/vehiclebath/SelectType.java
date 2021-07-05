package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SelectType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}