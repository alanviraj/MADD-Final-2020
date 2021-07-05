package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class AppointmentSummary extends AppCompatActivity {

    private String washType, time, date,logged,cType;
    private TextView Showtime, showDate, showWashType;
    private FloatingActionButton Rating, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_summary);

        washType = getIntent().getStringExtra("washType");
        time = getIntent().getStringExtra("time");
        date = getIntent().getStringExtra("date");
        logged = getIntent().getStringExtra("cno");
        cType = getIntent().getStringExtra("cType");



        Showtime = findViewById(R.id.Showtime);
        showDate = findViewById(R.id.showDate);
        showWashType = findViewById(R.id.showWashType);


        Showtime.setText("Time : " + time);
        showDate.setText("Date : "+ date);
        showWashType.setText("Type : " + washType);

        btnHome = (FloatingActionButton)findViewById(R.id.btnHome);
        Rating = (FloatingActionButton)findViewById(R.id.Rating);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectCarWash();
            }

            private void startSelectCarWash() {
                Intent intent = new Intent(AppointmentSummary.this, ViewUserLoyalty.class);
                intent.putExtra("cno",logged);
                intent.putExtra("cType",cType);
                startActivity(intent);
            }
        });

        Rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AppointmentSummary.this, UserRating.class);
                startActivity(intent1);
            }
        });

    }
}