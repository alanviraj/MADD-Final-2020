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

import com.example.vehiclebath.Model.Advertisement;
import com.example.vehiclebath.ViewHolder.AdvertisementViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class orgYourAdvertisement extends AppCompatActivity {

    private Intent intent = getIntent();
    private RecyclerView adRecyclerView;
    private DatabaseReference databaseReference;
    RecyclerView.LayoutManager layoutManager;
    Button addAdvertise;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_your_advertisement);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Advertisement");
        adRecyclerView = findViewById(R.id.adRecycler);
        adRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adRecyclerView.setLayoutManager(layoutManager);
        addAdvertise = findViewById(R.id.btnAdminAddAdvert);
        progressDialog = new ProgressDialog(this);

        addAdvertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orgYourAdvertisement.this,orgAddAdvertisement.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Advertisement> options = new FirebaseRecyclerOptions.Builder<Advertisement>()
                .setQuery(databaseReference,Advertisement.class).build();

        FirebaseRecyclerAdapter<Advertisement, AdvertisementViewHolder> adapter = new FirebaseRecyclerAdapter   <Advertisement, AdvertisementViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final AdvertisementViewHolder advertisementViewHolder, int i, @NonNull final Advertisement advertisement) {
                advertisementViewHolder.adName.setText(advertisement.getName());
                advertisementViewHolder.adDesc.setText(advertisement.getDescription());
                Picasso.get().load(advertisement.getImageUrl()).into(advertisementViewHolder.imageViewAd);


                advertisementViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(orgYourAdvertisement.this);
                        builder.setCancelable(true);
                        builder.setTitle("Delete Advertisement");
                        builder.setMessage("Are you sure?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressDialog.setTitle("Deleting Advertisements");
                                        progressDialog.setMessage("Deleting Records to Database");
                                        progressDialog.setCanceledOnTouchOutside(false);
                                        progressDialog.show();

                                        String url = advertisement.getImageUrl();
                                        String name = advertisement.getName();
                                        deleteAd(url,name);
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
            public AdvertisementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_advertisement,parent,false);
                AdvertisementViewHolder holder = new AdvertisementViewHolder(view);
                return holder;
            }
        };
        adRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public void deleteAd(final String url, final String name) {
        StorageReference deleteSRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        deleteSRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference deleteDRef = FirebaseDatabase.getInstance().getReference().child("Advertisement").child(name);
                deleteDRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(orgYourAdvertisement.this,"Advertisement Deleted",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(orgYourAdvertisement.this,"Advertisement Delete Failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(orgYourAdvertisement.this,"Advertisement Delete Failed",Toast.LENGTH_LONG).show();
            }
        });
    }


}