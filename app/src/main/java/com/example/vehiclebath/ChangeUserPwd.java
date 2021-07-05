package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangeUserPwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_pwd);

        Intent i = getIntent();

        Button btn_Change_PwdU;
        btn_Change_PwdU = findViewById(R.id.btn_change_pwdU);

        btn_Change_PwdU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeUserPwd.this,UserProfile.class);
                startActivity(intent);
            }
        });


    }
}