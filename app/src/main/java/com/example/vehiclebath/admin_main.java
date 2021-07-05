package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class admin_main extends AppCompatActivity {


    Button cusbutton,btnCarWashType,addButton,subButton,repButton,adminRatingsBtn,btnAppointments,btnSubUser;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mToolbar = (Toolbar) findViewById(R.id.register_user_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Admin Home");



//        orgbutton = findViewById(R.id.btnOrganization);
        cusbutton =  findViewById(R.id.btnCustomer);
        btnCarWashType = findViewById(R.id.btnCarWash);
        addButton = findViewById(R.id.btnAdd);
        subButton = findViewById(R.id.btnSub);
        repButton = findViewById(R.id.btnReoprtCustomer);
        adminRatingsBtn = findViewById(R.id.btnAdminRatings);
        btnAppointments = findViewById(R.id.btnAppointments);
        btnSubUser = findViewById(R.id.btnSubUser);


//        orgbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =  new Intent(admin_main.this,Orgnization.class);
//                startActivity(intent);
//            }
//        });

        cusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(admin_main.this,customers.class);
                startActivity(intent);
            }
        });

        btnCarWashType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(admin_main.this,adminAddCarwashType.class);
                startActivity(intent);
            }
        });

        repButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_main.this,CustomerReportedActivity.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_main.this,orgYourAdvertisement.class);
                startActivity(intent);
            }
        });

        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_main.this,adminSubscripDetail.class);
                startActivity(intent);
            }
        });


        btnSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_main.this,addSubscripUser.class);
                startActivity(intent);
            }
        });


        adminRatingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_main.this,AdminRating.class);
                startActivity(intent);
            }
        });


        btnAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_main.this,AppointmentbtnActivity.class);
                startActivity(intent);
            }
        });
    }
}