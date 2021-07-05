package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrgLocationInsert extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_location_insert);

        Intent i = getIntent();

        Button btn_Con_LocO;
        btn_Con_LocO = findViewById(R.id.btn_con_locO);

        btn_Con_LocO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgLocationInsert.this,ConfirmOrgRegistration.class);
                startActivity(intent);
            }
        });
    }
}