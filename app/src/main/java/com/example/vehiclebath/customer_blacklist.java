package com.example.vehiclebath;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class customer_blacklist extends AppCompatActivity {

    Intent i = getIntent();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_blacklist);

        button = (Button) findViewById(R.id.btnBlack1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }

    public void openDialog(){
        Dialog1 dialog1 = new Dialog1();
        dialog1.show(getSupportFragmentManager(),"Example Dialogue");
    }
/*
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want activate blocked user ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();



        super.onBackPressed();
    }

    */
}