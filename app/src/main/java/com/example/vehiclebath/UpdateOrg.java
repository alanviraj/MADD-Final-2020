package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateOrg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_org);

        Intent i = getIntent();

        Button btn_Update_Org;
        btn_Update_Org = findViewById(R.id.btn_update_org);

        btn_Update_Org.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateOrg.this,OrgProfile.class);
                startActivity(intent);
            }
        });
    }
}