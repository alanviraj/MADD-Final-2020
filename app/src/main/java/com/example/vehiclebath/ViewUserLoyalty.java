package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.Model.Subscription;
import com.example.vehiclebath.Prevalent1.Prevalent;
import com.example.vehiclebath.model1.Loyalty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewUserLoyalty extends AppCompatActivity {
    private TextView loyaltyValue;
    private Toolbar mToolbar;
    private String logged, cType;
    private Button nextPageLoyalty, return_home;
    private DatabaseReference databaseReference;
    float LP;
    float sumLoyalty;
   // float loyaltyT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_loyalty);

        logged = getIntent().getStringExtra("cno");
        cType = getIntent().getStringExtra("cType");

        mToolbar = (Toolbar) findViewById(R.id.register_user_toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Your Loyalty Points - Vehicle Bath");

        nextPageLoyalty = (Button) findViewById(R.id.btn_loyaltyR);
        return_home = (Button) findViewById(R.id.re_home);

        loyaltyValue = (TextView) findViewById(R.id.loyalty_value);

        float total = (float) 300.0;

        float LP1 = calLoyaltyPoints(total);
        String loyalty1 = "Your loyalty points are "+Float.toString(LP1);
        loyaltyValue.setText(loyalty1);

        if (!(cType.isEmpty())) {
            LP = calLoyaltyPoints(cType);
            String loyalty = "Your Loyalty Points Are "+ Float.toString(LP);
            loyaltyValue.setText(loyalty);
        }
       else {
           LP = retrieveLoyalty();
            String loyalty = "Your Loyalty Points Are "+ Float.toString(LP);
            loyaltyValue.setText(loyalty);
        }


        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               totalSumLoyalty(LP);

            }
        });

        //Toast.makeText(this, "HH"+LP, Toast.LENGTH_SHORT).show();

       final String lp = String.valueOf(LP);

        nextPageLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ViewUserLoyalty.this, redeemloyalty.class);
                Toast.makeText(ViewUserLoyalty.this, "HHkl"+lp, Toast.LENGTH_SHORT).show();
                intent.putExtra("cno",logged);
                intent.putExtra("lPoints2",lp);
                //intent.putExtra("lPoints",lp);
                startActivity(intent);
            }
        });

    }

    public float calLoyaltyPoints (float total) {
        float LoyaltyP = (float) (total*0.1);
        return LoyaltyP;

    }


    private void totalSumLoyalty(final float lp) {

        final float lPoints = lp;

       final DatabaseReference RootRef;
       // DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Users").child(logged).child("LoyaltyPoints").exists())) {
                    RootRef.child("Users").child(logged).child("LoyaltyPoints").setValue(lPoints).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               startActivity(new Intent(ViewUserLoyalty.this,HomeUserActivity.class));
                               Toast.makeText(ViewUserLoyalty.this, "Loyalty Points Successfully Added!" , Toast.LENGTH_SHORT).show();
                           }
                           else {
                               startActivity(new Intent(ViewUserLoyalty.this,HomeUserActivity.class));
                               Toast.makeText(ViewUserLoyalty.this, "Loyalty Points Added Failed!" , Toast.LENGTH_SHORT).show();
                           }
                        }
                    });


                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference = databaseReference.child("Users");
                    databaseReference.child(logged).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final Loyalty loyal = snapshot.getValue(Loyalty.class);
                            float pastLoyalty = loyal.getLoyaltyPoints();
                            float sumLoyalty = pastLoyalty + lPoints;

                            databaseReference.child(logged).child("LoyaltyPoints").setValue(sumLoyalty).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(ViewUserLoyalty.this,HomeUserActivity.class));
                                        Toast.makeText(ViewUserLoyalty.this, "Loyalty Points Added!" , Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        startActivity(new Intent(ViewUserLoyalty.this,HomeUserActivity.class));
                                        Toast.makeText(ViewUserLoyalty.this, "Loyalty Points Failed!" , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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

    }

    public float calLoyaltyPoints (String cType) {
        float LoyaltyP = 0;

        if (cType.equals("Wax") || cType.equals("vaccum") || cType.equals("Body Wash")) {
            LoyaltyP = (float) 300.0 * (float) 0.1;
        }
        else if (cType.equals("Interior Cleaning")) {
            LoyaltyP = (float) 4500.0 * (float) 0.1;
        }
//        else {
//            Toast.makeText(ViewUserLoyalty.this,"Error!",Toast.LENGTH_LONG).show();
//        }
        return LoyaltyP;
    }

    private float retrieveLoyalty() {

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("Users");
        databaseReference.child(logged).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final Loyalty loyal = snapshot.getValue(Loyalty.class);
                float pastLoyalty = loyal.getLoyaltyPoints();
                sumLoyalty = pastLoyalty;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return sumLoyalty;
    }

}