package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.Model.Users;
import com.example.vehiclebath.UserHolder.UserViewHolder;
import com.example.vehiclebath.UserHolder.UserViewHolderReport;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CustomerReportActivity extends AppCompatActivity {


    private TextView reportName,reportPhone,reportPassword,reportEmail;
    private ProgressDialog loadingBar;


    Intent i = getIntent();
    private DatabaseReference UserRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_report);


        UserRef = FirebaseDatabase.getInstance().getReference().child("UsersReport");

        recyclerView = findViewById(R.id.recyle_report);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(UserRef,Users.class)
                        .build();

        FirebaseRecyclerAdapter<Users, UserViewHolderReport> adapter =
                new FirebaseRecyclerAdapter<Users, UserViewHolderReport>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolderReport holder, int position, @NonNull Users model) {

                        holder.UserName.setText(model.getName());
                        holder.UserEmail.setText(model.getEmail());
                        holder.UserPassword.setText(model.getPassword());
                        holder.UserPhone.setText(model.getPhone());



                    }

                    @NonNull
                    @Override
                    public UserViewHolderReport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_report_layout,parent,false);
                        UserViewHolderReport holder = new UserViewHolderReport(view);
                        return holder;

                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}