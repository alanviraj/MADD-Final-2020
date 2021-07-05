package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterOrg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_org);

        Intent i = getIntent();

        Button btn_Org_Reg;
        btn_Org_Reg = findViewById(R.id.btn_org_reg);

        btn_Org_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterOrg.this,ChangeUserPwd.class);
                startActivity(intent);
            }
        });

    }
}