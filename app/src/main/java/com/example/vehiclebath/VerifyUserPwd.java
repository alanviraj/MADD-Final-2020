package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifyUserPwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user_pwd);

        Intent i = getIntent();

        Button btn_Verify_AccU;
        btn_Verify_AccU= findViewById(R.id.btn_verify_accU);

        btn_Verify_AccU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyUserPwd.this,ChangeUserPwd.class);
                startActivity(intent);
            }
        });

    }
}