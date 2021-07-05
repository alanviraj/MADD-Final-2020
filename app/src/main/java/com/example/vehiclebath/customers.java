package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vehiclebath.Model.Users;
import com.example.vehiclebath.UserHolder.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class customers extends AppCompatActivity {


    Intent i = getIntent();
    private DatabaseReference UserRef,UsersTot;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private int sum = 0;
    private TextView tvUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);


        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView = findViewById(R.id.recyle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tvUser = findViewById(R.id.userSum);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UsersTot = FirebaseDatabase.getInstance().getReference().child("Users");


        //---------------------------------SUM-------------------------------------//

        UsersTot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    sum = (int)snapshot.getChildrenCount();
                    tvUser.setText(Integer.toString(sum)+" Users");
                }else
                {
                    tvUser.setText("0 Users");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //---------------------------------SUM-------------------------------------//





    }






    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> options =
        new FirebaseRecyclerOptions.Builder<Users>()
                .setQuery(UserRef,Users.class)
                .build();

        FirebaseRecyclerAdapter<Users, UserViewHolder> adapter =
                new FirebaseRecyclerAdapter<Users, UserViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull final Users model) {

                        holder.UserName.setText(model.getName());
                        holder.UserEmail.setText(model.getEmail());
                        holder.UserPassword.setText(model.getPassword());
                        holder.UserPhone.setText(model.getPhone());



                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(customers.this,OrgnizationReport_or_Activity.class);
                                intent.putExtra("UserPhone",model.getPhone());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_layout,parent,false);
                        UserViewHolder holder = new UserViewHolder(view);
                        return holder;

                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}