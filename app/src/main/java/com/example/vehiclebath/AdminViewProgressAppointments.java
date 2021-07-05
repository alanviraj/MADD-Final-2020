package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vehiclebath.CarWashTypeHolder.adminProgressViewHolder;
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

public class AdminViewProgressAppointments extends AppCompatActivity {

    private RecyclerView tableproressrecycler;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_progress_appointments);

        reference = FirebaseDatabase.getInstance().getReference().child("ProgressAppointments");
        tableproressrecycler = findViewById(R.id.tableproressrecycler);
        tableproressrecycler.setHasFixedSize(true);
        tableproressrecycler.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Appointments> options =
                new FirebaseRecyclerOptions.Builder<Appointments>().setQuery(reference, Appointments.class).build();

        FirebaseRecyclerAdapter<Appointments, adminProgressViewHolder> adapter =
                new FirebaseRecyclerAdapter<Appointments, adminProgressViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull adminProgressViewHolder holder, int position, @NonNull Appointments model) {
                        holder.CName.setText(model.getC_Name());
                        holder.type.setText(model.getCarWashType());
                        final String date =  model.getDate()+ "  " + model.getTime();
                        holder.dateTime.setText(date);



//                        final String Aid = "A"+dateI+"_"+time;

                        final String washType, cName;

                        washType = model.getCarWashType();
                        cName = model.getC_Name();
                        final String dateI =  model.getDate();
                        final String time = model.getTime();


                        holder.pbtnF.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                createFinishedAppointment(cName, washType, dateI, time );
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public adminProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_progress_table_layout,parent,false);
                        adminProgressViewHolder holder = new adminProgressViewHolder(view);
                        return holder;

                    }
                };
        tableproressrecycler.setAdapter(adapter);
        adapter.startListening();
    }

    private void createFinishedAppointment(final String cName, final String washType, final String date, final String time) {
        final String key = "A"+date.replace("/","")+"_"+time ;

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("FinishedAppointments").child(key).exists())){
                    HashMap<String, Object> appdata = new HashMap<>();
                    appdata.put("Date",date);
                    appdata.put("Time",time);
                    appdata.put("CarWashType", washType);
                    appdata.put("C_Name", cName);

                    ref.child("FinishedAppointments").child(key).updateChildren(appdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AdminViewProgressAppointments.this, "Appointment Finished Successfully", Toast.LENGTH_LONG).show();
                                ref.child("ProgressAppointments").child(key).removeValue();
                            }
                            else{
                                Toast.makeText(AdminViewProgressAppointments.this, "Error", Toast.LENGTH_LONG).show();
                            }
//                            Intent intent =  new Intent(AdminViewProgressAppointments.this, Success.class);
//                            startActivity(intent);
                        }
                    });

                }
                else{
                    Toast.makeText(AdminViewProgressAppointments.this, "Error !Duplicate entry.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


}
