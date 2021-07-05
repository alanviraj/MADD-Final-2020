package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmOrgRegistration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_org_registration);

        Intent i = getIntent();

        Button btn_Create_OA;
        btn_Create_OA = findViewById(R.id.btn_create_OA);

        btn_Create_OA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmOrgRegistration.this,LoginNew.class);
                startActivity(intent);
            }
        });

    }
}