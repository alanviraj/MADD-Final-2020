package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import io.paperdb.Paper;

public class UserHomePge extends AppCompatActivity {

    private Button logoutbtn1;
    private DatabaseReference AdvertisementRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_pge);

        logoutbtn1 = (Button) findViewById(R.id.logout1);

        logoutbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Paper.book().destroy();

                Intent intent = new Intent(UserHomePge.this,LoginNew.class);
                startActivity(intent);
            }
        });

        Intent i = getIntent();

        Button btn_View_Loyalty;
        btn_View_Loyalty = findViewById(R.id.btn_view_loyalty);

        btn_View_Loyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePge.this,ViewUserLoyalty.class);
                startActivity(intent);
            }
        });

        FloatingActionButton btn_View_UserP;
        btn_View_UserP = findViewById(R.id.btn_view_userProfile);

        btn_View_UserP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePge.this,UserProfile.class);
                startActivity(intent);
            }
        });

        //Appointments

        Button btn_viewAppointments = findViewById(R.id.btn_view_vAppointments);

        btn_viewAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomePge.this,SelectCarWash.class);
            }
        });


    }
}