package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class report_customer_message extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_customer_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button = (Button) findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }


    public void openDialog(){
        Dialog2 dialog2 = new Dialog2();
        dialog2.show(getSupportFragmentManager(),"Example Dialogue");
    }
}