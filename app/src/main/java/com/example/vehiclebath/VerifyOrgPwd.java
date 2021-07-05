package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifyOrgPwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_org_pwd);

        Intent i = getIntent();

        Button btn_V_Button;
        btn_V_Button = findViewById(R.id.btn_verify_accO);

        btn_V_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyOrgPwd.this,ChangeOrgPwd.class);
                startActivity(intent);
            }
        });
    }
}