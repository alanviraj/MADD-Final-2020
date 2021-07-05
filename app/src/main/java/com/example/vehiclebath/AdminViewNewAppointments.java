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
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.CarWashTypeHolder.AdminAppointmentTableViewHolder;
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

public class AdminViewNewAppointments extends AppCompatActivity {

    private RecyclerView table_recycler;
    private DatabaseReference reference;

    private DatabaseReference count;
    private int sum = 0;
    private TextView countHeader;
    private String Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_new_appointments);
        setContentView(R.layout.activity_admin_view_new_appointments);

        reference = FirebaseDatabase.getInstance().getReference().child("ClashAppointments");
        table_recycler = findViewById(R.id.tablerowrecycler);
        table_recycler.setHasFixedSize(true);
        table_recycler.setLayoutManager(new LinearLayoutManager(this));

        countHeader = findViewById(R.id.countHeader);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    sum = (int) snapshot.getChildrenCount();
                    countHeader.setText("Count : " + Integer.toString(sum));
                }
                else{
                    countHeader.setText("Count : 0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Appointments> options =
                new FirebaseRecyclerOptions.Builder<Appointments>().setQuery(reference, Appointments.class).build();

        FirebaseRecyclerAdapter<Appointments, AdminAppointmentTableViewHolder> adapter =
                new FirebaseRecyclerAdapter<Appointments, AdminAppointmentTableViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminAppointmentTableViewHolder holder, int position, @NonNull Appointments model) {

                        final String Key = model.getKey();


                        holder.C_Name.setText(model.getC_Name());
                        holder.type.setText(model.getCarWashType());
                        final String dateI =  model.getDate()+ "  " + model.getTime();
                        holder.dateTime.setText(dateI);

//                        dateI =  model.getDate().replace("/","");

                        final String time = model.getTime();

                        final String washType, cName;

                        washType = model.getCarWashType();
                        cName = model.getC_Name();

                        final String Aid = "A"+dateI+"_"+time;

                        holder.btnF.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //createProgressAppointment(cName, washType, date, time );
                                Intent intent = new Intent(AdminViewNewAppointments.this, AdminUpdateAppointment.class );
                                intent .putExtra("cName", cName );
                                intent .putExtra("washType", washType );
                                intent .putExtra("date", dateI );
                                intent .putExtra("time", time );
                                intent.putExtra("Key", Key);
                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminAppointmentTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_table_layout,parent,false);
                        AdminAppointmentTableViewHolder holder = new AdminAppointmentTableViewHolder(view);
                        return holder;
                    }
                };
        table_recycler.setAdapter(adapter);
        adapter.startListening();
    }

//    private void createProgressAppointment(final String cName, final String washType, final String date, final String time) {
//
//        final String key = "A"+date.replace("/","")+"_"+time ;
//
//        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @SuppressLint("ShowToast")
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(!(dataSnapshot.child("ProgressAppointments").child(key).exists())){
//                    HashMap<String, Object> appdata = new HashMap<>();
//                    appdata.put("Date",date);
//                    appdata.put("Time",time);
//                    appdata.put("CarWashType", washType);
//                    appdata.put("Customer",cName);
//
//                    ref.child("ProgressAppointments").child(key).updateChildren(appdata).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(AdminViewNewAppointments.this, "Appointment Placed Successfully", Toast.LENGTH_LONG).show();
//                                ref.child("ClashAppointments").child(key).removeValue();
//                            }
//                            else{
//                                Toast.makeText(AdminViewNewAppointments.this, "Error", Toast.LENGTH_LONG).show();
//                            }
////                            Intent intent =  new Intent(AdminViewProgressAppointments.this, Success.class);
////                            startActivity(intent);
//                        }
//                    });
//
//                }
//                else{
//                    Toast.makeText(AdminViewNewAppointments.this, "Error", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }


}
