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
import android.widget.TextView;

import com.example.vehiclebath.Model.Users;
import com.example.vehiclebath.Model.UsersReport;
import com.example.vehiclebath.UserHolder.UserViewHolderReport;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomerReportedActivity extends AppCompatActivity {

    private TextView reportName,reportPhone,reportPassword,reportEmail;
    private ProgressDialog loadingBar;


    Intent i = getIntent();
    private DatabaseReference UserRepRef,ReportedUsersTot;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private int sum = 0;
    private TextView tvUserReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reported);


        UserRepRef = FirebaseDatabase.getInstance().getReference().child("UsersReport");

        recyclerView = findViewById(R.id.recyle_reported);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        tvUserReport = findViewById(R.id.userReportSum);

        ReportedUsersTot = FirebaseDatabase.getInstance().getReference().child("UsersReport");


        //---------------------------------SUM-------------------------------------//

        ReportedUsersTot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    sum = (int)snapshot.getChildrenCount();
                    tvUserReport.setText(Integer.toString(sum)+" Users");
                }else
                {
                    tvUserReport.setText("0 Users");
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

        FirebaseRecyclerOptions<UsersReport> options=
        new FirebaseRecyclerOptions.Builder<UsersReport>()
                .setQuery(UserRepRef,UsersReport.class).build();

//        FirebaseRecyclerOptions<Users> options =
//                new FirebaseRecyclerOptions.Builder<Users>()
//                        .setQuery(UserRef,Users.class)
//                        .build();

        FirebaseRecyclerAdapter<UsersReport, UserViewHolderReport> adapter =
                new FirebaseRecyclerAdapter<UsersReport, UserViewHolderReport>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserViewHolderReport holder, int position, @NonNull final UsersReport model) {

                        holder.UserName.setText(model.getName());
                        holder.UserEmail.setText(model.getEmail());
                        holder.UserPassword.setText(model.getPassword());
                        holder.UserPhone.setText(model.getPhone());


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(CustomerReportedActivity.this,OrganizationReport_or_Activate_Activity.class);
                                intent.putExtra("UserPhone",model.getPhone());
                                startActivity(intent);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public UserViewHolderReport onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_reported_layout,parent,false);
                        UserViewHolderReport holder = new UserViewHolderReport(view);
                        return holder;

                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}