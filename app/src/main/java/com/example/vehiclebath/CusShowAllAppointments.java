package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CusShowAllAppointments extends AppCompatActivity {

    TextView Date, Time, Type;
    DatabaseReference ref;
    Button btnDlt;
    List<Appointments> app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_show_all_appointments);

        Date = findViewById(R.id.cusWashDate);
        Time = findViewById(R.id.cusWashTime);
        Type = findViewById(R.id.custypeName);
        btnDlt = findViewById(R.id.btnDlt);

        app = new ArrayList<>();


        btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query query = FirebaseDatabase.getInstance().getReference("ProgressAppointments")
                        .orderByChild("C_Name")
                        .equalTo("Agil");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Appointments appointments = snapshot.getValue(Appointments.class);
                                app.add(appointments);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });




    }

}