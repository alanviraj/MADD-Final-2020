package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrgProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_profile);

        Intent i = getIntent();

        Button btn_Upd_Org;
        btn_Upd_Org = findViewById(R.id.btn_upd_org1);



        btn_Upd_Org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgProfile.this,UpdateOrg.class);
                startActivity(intent);
            }
        });

        Button btn_Upd_Loc;
        btn_Upd_Loc = findViewById(R.id.btn_upd_loc1);

        btn_Upd_Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrgProfile.this,OrgLocationUpdate.class);
                startActivity(intent);
            }
        });


    }
}