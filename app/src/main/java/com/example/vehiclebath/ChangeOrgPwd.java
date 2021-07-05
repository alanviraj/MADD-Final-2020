package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeOrgPwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_org_pwd);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        Button btn_Org_Chgpwd;
        btn_Org_Chgpwd = findViewById(R.id.btn_org_chgpwd);

        btn_Org_Chgpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeOrgPwd.this,OrgProfile.class);
                startActivity(intent);
            }
        });
    }
}