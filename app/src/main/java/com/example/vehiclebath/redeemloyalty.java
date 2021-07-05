package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.model1.Loyalty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class redeemloyalty extends AppCompatActivity {

    private Toolbar mToolbar;
    private Spinner spinnerLoyalty;
    private Button conRLoyalty;
    private TextView retrieveLoyal;
    private String logged , lpoints;
    //private float lpoints;
    private DatabaseReference databaseReference;
    float sumLoyalty = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeemloyalty);

        logged = getIntent().getStringExtra("cno");
        lpoints = getIntent().getStringExtra("lPoints2");
       // lpoints = getIntent().getFloatExtra("lPoints");

        Toast.makeText(this, "ab"+lpoints, Toast.LENGTH_SHORT).show();

        mToolbar = (Toolbar) findViewById(R.id.register_user_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Total Loyalty Points");


        spinnerLoyalty = (Spinner) findViewById(R.id.opt_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.optionLoyalty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoyalty.setAdapter(adapter);


        retrieveLoyal = (TextView) findViewById(R.id.dis_totP);
        conRLoyalty = (Button) findViewById(R.id.btn_conLoyal);
        spinnerLoyalty = (Spinner) findViewById(R.id.opt_spinner);

        final float loyaltyPI =  chkLoyalty(lpoints);
        String loyal = String.valueOf(loyaltyPI);
        retrieveLoyal.setText(loyal);


        conRLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]
                        {
                                "Yes",
                                "No"
                        };
                AlertDialog.Builder builder = new AlertDialog.Builder(redeemloyalty.this);
                builder.setTitle("Do you want to redeem your loyalty points? ");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0)
                        {

                            reduceLoyalty(loyaltyPI);
                            finish();
                        }
                        else
                        {
                            startActivity(new Intent(redeemloyalty.this,redeemloyalty.class));
                            finish();
                        }
                    }
                });
                builder.show();

            }
        });




    }

    private void reduceLoyalty(float loyaltyPI) {
        float finalPoints = 0;
        int points = 0;
        final String loyaltyPack = spinnerLoyalty.getSelectedItem().toString().trim();
        if (loyaltyPack.equals("Premium-50")) {
            finalPoints = loyaltyPI - 50;
        }
        else if (loyaltyPack.equals("Classis-100")) {
            finalPoints = loyaltyPI - 100;
        }
        else  if (loyaltyPack.equals("Hybrid-150")) {
            finalPoints = loyaltyPI - 150;
        }
        else if (loyaltyPack.equals("Free Wash-200")) {
            finalPoints = loyaltyPI - 200;
        }

        finalreduction(finalPoints);

    }

    private void finalreduction(float finalPoints) {
        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference().child("Users");
        rootref.child(logged).child("LoyaltyPoints").setValue(finalPoints).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(redeemloyalty.this,redeemloyalty.class));
                    Toast.makeText(redeemloyalty.this, "Loyalty Points Redeemed Successfully!" , Toast.LENGTH_SHORT).show();
                }
                else  {
                    startActivity(new Intent(redeemloyalty.this,redeemloyalty.class));
                    Toast.makeText(redeemloyalty.this, "Loyalty Points Redeemed Failed!" , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private float chkLoyalty(final String lpoints) {


        final DatabaseReference RootRef;
       // Toast.makeText(this, "hh"+lpoints, Toast.LENGTH_SHORT).show();

        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(logged).child("LoyaltyPoints").exists())) {
                    sumLoyalty = Float.parseFloat(String.valueOf(lpoints));
                   //m Toast.makeText(redeemloyalty.this, "kaka", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(redeemloyalty.this, "jkjk", Toast.LENGTH_SHORT).show();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference = databaseReference.child("Users");
                    databaseReference.child(logged).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final Loyalty loyal = snapshot.getValue(Loyalty.class);
                            float pastLoyalty = loyal.getLoyaltyPoints();
                            //Toast.makeText(redeemloyalty.this, "opop"+pastLoyalty, Toast.LENGTH_SHORT).show();
                            float points = Float.parseFloat(String.valueOf(lpoints));
                            sumLoyalty = pastLoyalty + points;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return sumLoyalty;

    }



}

