package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent i = getIntent();

        Button btn_Verify_PwdU;
        btn_Verify_PwdU = findViewById(R.id.btn_verify_pwdU);

        btn_Verify_PwdU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, VerifyUserPwd.class);
                startActivity(intent);
            }
        });

        Button btn_Upd_User;
        btn_Upd_User = findViewById(R.id.btn_upd_user);

        btn_Upd_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this, UpdateUser.class);
                startActivity(intent);
            }
        });




    }
}