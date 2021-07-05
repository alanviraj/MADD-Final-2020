package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.PriorityQueue;

public class AdminUpdateAppointment extends AppCompatActivity {

    private EditText eACusName, eADate, EAtime, EAtime2;
    private Button updateAppointment;
    private String newTime, key;
    private String key1, nDate, nTime;

    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_appointment);

        eACusName = findViewById(R.id.eACusName);
        eADate = findViewById(R.id.eADate);
        EAtime = findViewById(R.id.EAtime);
        EAtime2 = findViewById(R.id.EAtime2);

        final String date =getIntent().getStringExtra("date");
        final String time =getIntent().getStringExtra("time");
        key1 = getIntent().getStringExtra("Key");

        eACusName.setText(getIntent().getStringExtra("cName"));
        eADate.setText(getIntent().getStringExtra("washType"));
        EAtime.setText(date);
        EAtime2.setText(time);

        key = "A"+date.replace("/","")+"_"+time ;

        ref = FirebaseDatabase.getInstance().getReference();

        updateAppointment = findViewById(R.id.updateAppointment);

        updateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateExistAppointment();
            }
        });

    }

    private void updateExistAppointment() {
        newTime = EAtime2.getText().toString();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("ProgressAppointments").child(key).exists())) {
                    HashMap<String, Object> appdata = new HashMap<>();
                    appdata.put("Date", EAtime.getText().toString().replace("/","") );
                    appdata.put("Time", EAtime2.getText().toString());
                    appdata.put("CarWashType", eADate.getText().toString());
                    appdata.put("Key", key);
                    appdata.put("C_Name", eACusName.getText().toString());

                    ref.child("ProgressAppointments").child(key).
                            updateChildren(appdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AdminUpdateAppointment.this, "Success", Toast.LENGTH_LONG).show();
                                ref.child("ClashAppointments").child(key1).removeValue();
                                Intent intent = new Intent(AdminUpdateAppointment.this, admin_main.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(AdminUpdateAppointment.this, "We already have an Appointment on this time", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
                else{
                    Toast.makeText(AdminUpdateAppointment.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}