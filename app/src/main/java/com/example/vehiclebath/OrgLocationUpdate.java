package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrgLocationUpdate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_location_update);

        Intent i = getIntent();

        Button btn_Con_ULoc ;
        btn_Con_ULoc = findViewById(R.id.btn_con_uLoc );

        btn_Con_ULoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgLocationUpdate.this,OrgProfile.class);
                startActivity(intent);
            }
        });
    }
}