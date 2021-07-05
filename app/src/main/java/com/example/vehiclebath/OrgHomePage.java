package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OrgHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_home_page);

        Intent i = getIntent();

        FloatingActionButton btn_View_OrgP;
        btn_View_OrgP = findViewById(R.id.btn_view_orgP);

        btn_View_OrgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgHomePage.this,OrgProfile.class);
                startActivity(intent);
            }
        });


    }
}