package com.example.vehiclebath;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vehiclebath.Model.Subscription;
import com.example.vehiclebath.ViewHolder.SubscriptionViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminSubscripDetail extends AppCompatActivity {

    private Intent intent = getIntent();
    private RecyclerView subRecyclerView;
    private DatabaseReference databaseReference;
    RecyclerView.LayoutManager layoutManager;
    private Button btnAddSub;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_subscrip_detail);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Subscription");
        subRecyclerView = findViewById(R.id.subRecycler);
        subRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        subRecyclerView.setLayoutManager(layoutManager);
        btnAddSub = findViewById(R.id.btnAdminAddSub1);
        progressDialog = new ProgressDialog(this);

        btnAddSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(adminSubscripDetail.this,addSubscrip.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Subscription> options = new FirebaseRecyclerOptions.Builder<Subscription>()
                .setQuery(databaseReference,Subscription.class).build();

        FirebaseRecyclerAdapter<Subscription, SubscriptionViewHolder> adapter = new FirebaseRecyclerAdapter   <Subscription, SubscriptionViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SubscriptionViewHolder subscriptionViewHolder, int i, @NonNull final Subscription subscription) {
                subscriptionViewHolder.subName.setText(subscription.getName());
                subscriptionViewHolder.subPrice.setText(subscription.getPrice());
                subscriptionViewHolder.subDPerc.setText(subscription.getDiscountPercentage());
                subscriptionViewHolder.subAvailability.setText(subscription.getAvailability());


                subscriptionViewHolder.btnSubDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(adminSubscripDetail.this);
                        builder.setCancelable(true);
                        builder.setTitle("Delete Subscription");
                        builder.setMessage("Are you sure?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog.setTitle("Deleting Subscription");
                                        progressDialog.setMessage("Deleting Records to Database");
                                        progressDialog.setCanceledOnTouchOutside(false);
                                        progressDialog.show();

                                        String name = subscription.getName();
                                        deleteSub(name);
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }

            @NonNull
            @Override
            public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_subscription,parent,false);
                SubscriptionViewHolder holder = new SubscriptionViewHolder(view);
                return holder;
            }
        };
        subRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deleteSub(String name) {

        DatabaseReference deleteDRef = FirebaseDatabase.getInstance().getReference().child("Subscription").child(name);
        deleteDRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(adminSubscripDetail.this,"Subscription Deleted",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(adminSubscripDetail.this,"Subscription Delete Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

}