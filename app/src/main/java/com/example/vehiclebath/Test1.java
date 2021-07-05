package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Test1 extends AppCompatActivity {
    private TextView abcd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        String lp;

        lp = getIntent().getStringExtra("lPoints");
        abcd = (TextView) findViewById(R.id.abcd);

        Toast.makeText(this, "ABCD"+lp, Toast.LENGTH_SHORT).show();





    }
}