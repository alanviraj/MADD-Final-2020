package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppointmentbtnActivity extends AppCompatActivity {

    Button btn_Clashed_apnt, btn_Progress_apnt, btn_Finished_apnt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointmentbtn);

        btn_Finished_apnt = findViewById(R.id.btn_Finished_apnt);
        btn_Progress_apnt = findViewById(R.id.btn_Progress_apnt);
        btn_Clashed_apnt = findViewById(R.id.btn_Clashed_apnt);

        btn_Clashed_apnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentbtnActivity.this, AdminViewNewAppointments.class);
                startActivity(intent);
            }
        });

        btn_Progress_apnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentbtnActivity.this, AdminViewProgressAppointments.class);
                startActivity(intent);
            }
        });

        btn_Finished_apnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentbtnActivity.this, adminViewAllAppointments.class);
                startActivity(intent);
            }
        });

    }
}