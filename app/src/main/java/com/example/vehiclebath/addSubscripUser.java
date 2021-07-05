package com.example.vehiclebath;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.example.vehiclebath.Model.Subscription;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class addSubscripUser extends AppCompatActivity {

    private Spinner subPlan;
    private EditText phone,subPeriod;
    private Button btnAddUserSub;
    private DatabaseReference databaseReference;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscrip_user);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();
        subPlan = findViewById(R.id.spinnerSubPlan);
        phone = findViewById(R.id.etUserPhone);
        subPeriod = findViewById(R.id.etSubPeriod);
        btnAddUserSub = findViewById(R.id.btnAdminAddUserSub);
        arrayAdapter = new ArrayAdapter<String>(addSubscripUser.this,android.R.layout.simple_spinner_dropdown_item,arrayList);
        progressDialog = new ProgressDialog(this);

        subPlan.setAdapter(arrayAdapter);
        getSubPlans();

        btnAddUserSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserSubscription();
            }
        });

    }

    private void getSubPlans() {
        databaseReference.child("Subscription").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot item:snapshot.getChildren()){
                    arrayList.add(item.child("Name").getValue().toString());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addUserSubscription() {
        final String phoneNumber = phone.getText().toString().trim();
        final String subscriptionPlan = subPlan.getSelectedItem().toString().trim();
        final String subscriptionPeriod = subPeriod.getText().toString().trim();

        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(subscriptionPlan)){
            Toast.makeText(this,"Please Select Subscription Plan",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(subscriptionPeriod)){
            Toast.makeText(this,"Please Enter Subscription Period",Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Inserting Subscription Plan for User");
            progressDialog.setMessage("Adding Records to Database");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            chkDetail(phoneNumber,subscriptionPlan,subscriptionPeriod);
        }
    }

    private void chkDetail(final String phoneNumber, final String subscriptionPlan, final String subscriptionPeriod) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phoneNumber).exists()){
                    DatabaseReference dataRef1 = databaseReference.child("Subscription");
                    dataRef1.child(subscriptionPlan).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final Subscription sub = snapshot.getValue(Subscription.class);
                            int subPeriod = Integer.parseInt(subscriptionPeriod);
                            int price = Integer.parseInt(sub.getPrice());
                            //int totSubAmount = Integer.parseInt(sub.getPrice()) * subPeriod;

                            double totAmount =  calculateSubAmount(price,subPeriod);

                            AlertDialog.Builder builder = new AlertDialog.Builder(addSubscripUser.this);
                            builder.setCancelable(true);
                            builder.setTitle("Confirm Subscription Type");
                            builder.setMessage("Subscription Amount: Rs."+totAmount+"\n\nDo you wish to continue?");
                            builder.setPositiveButton("Confirm",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            updateSubcriptionPlan(subscriptionPlan,phoneNumber,subscriptionPeriod);
                                        }
                                    });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                            Toast.makeText(addSubscripUser.this,"Failed to Update Data!!!",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(addSubscripUser.this,"Added Phone Number does not Exist!!!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }

    public double calculateSubAmount(int price, int subPeriod) {
        return price*subPeriod;
    }

    private void updateSubcriptionPlan(String subscriptionPlan, String phoneNumber, final String subscriptionPeriod) {
        final DatabaseReference dataRef2 = databaseReference.child("Users").child(phoneNumber);
        dataRef2.child("subscriptionPlan").setValue(subscriptionPlan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    dataRef2.child("subscriptionPeriod").setValue(subscriptionPeriod).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(addSubscripUser.this,"New Subscription Plan and Subscription Period for User is Added",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(addSubscripUser.this,adminSubscripDetail.class);
                                startActivity(intent);
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(addSubscripUser.this,"Failed to Add Subscription Period for User",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(addSubscripUser.this,"Failed to Add New Subscription Plan for User",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}