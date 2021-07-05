package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Orgnization extends AppCompatActivity {

    Intent i = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgnization);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}