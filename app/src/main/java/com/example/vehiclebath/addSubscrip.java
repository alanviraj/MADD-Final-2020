package com.example.vehiclebath;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class addSubscrip extends AppCompatActivity {

    private EditText subName,subPrice,subdPerc;
    private Spinner spinner;
    private Button btnAddSub;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscrip);

        Intent intent = getIntent();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        spinner = (Spinner) findViewById(R.id.spinnerSubAvail);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Availability, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        subName = findViewById(R.id.etSubName);
        subPrice = findViewById(R.id.etSubPrice);
        subdPerc = findViewById(R.id.etSubdPerc);
        btnAddSub = findViewById(R.id.btnAdminAddSub2);
        progressDialog = new ProgressDialog(this);

        btnAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubscription();
            }
        });
    }

    private void addSubscription() {
        String subscriptionName = subName.getText().toString().trim();
        String subscriptionPrice = subPrice.getText().toString().trim();
        String subscriptionDPercentage = subdPerc.getText().toString().trim();
        String subscriptionAvailability = spinner.getSelectedItem().toString().trim();

        if(TextUtils.isEmpty(subscriptionName)){
            Toast.makeText(this,"Please Enter Subscription Name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(subscriptionPrice)){
            Toast.makeText(this,"Please Enter Subscription Price",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(subscriptionAvailability)){
            Toast.makeText(this,"Please Select Subscription Availability",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(subscriptionDPercentage)){
            Toast.makeText(this,"Please Select Subscription Discount Percentage",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Inserting Subscription");
            progressDialog.setMessage("Adding Records to Database");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            addSubscriptionToDB(subscriptionName,subscriptionPrice,subscriptionDPercentage,subscriptionAvailability);
        }
    }

    private void addSubscriptionToDB(final String subscriptionName, final String subscriptionPrice,final String subscriptionDPercentage, final String subscriptionAvailability) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Subscription").child(subscriptionName).exists())){
                    HashMap<String,Object> subDataMap = new HashMap<>();
                    subDataMap.put("Name",subscriptionName);
                    subDataMap.put("Price",subscriptionPrice);
                    subDataMap.put("DiscountPercentage",subscriptionDPercentage);
                    subDataMap.put("Availability",subscriptionAvailability);

                    RootRef.child("Subscription").child(subscriptionName).updateChildren(subDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(addSubscrip.this,"New Subscription Plan is Added",Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(addSubscrip.this,adminSubscripDetail.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        progressDialog.dismiss();
                                        Toast.makeText(addSubscrip.this,"Failed to Add New Subscription Plan",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(addSubscrip.this,"Added Subscrpition Plan Already Exist",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }

}