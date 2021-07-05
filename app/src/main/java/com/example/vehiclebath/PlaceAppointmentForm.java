package com.example.vehiclebath;

import androidx.annotation.RequiresApi;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.vehiclebath.Prevalent1.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PlaceAppointmentForm extends AppCompatActivity {
    //Date Picker
    private DatePickerDialog picker;
    private EditText dateText;
    //Time Picker
    private TimePickerDialog Tpicker;
    private EditText TimeText;
    //spinner
    private Spinner spinner;
    private Button btn_addTypeDB;
    private ProgressDialog loadingBar;

    private EditText carWashtypeName;
    private String carWashTypeVal, logged;
    private DatabaseReference SessionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_appointment_form);

        logged = getIntent().getStringExtra("logged");

        loadingBar = new ProgressDialog(this);

        carWashTypeVal = getIntent().getStringExtra("Type");

        carWashtypeName = findViewById(R.id.fm_serviceType);
        carWashtypeName.setText(carWashTypeVal);

        //Spinner Type
        spinner = findViewById(R.id.fm_vehicleType);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("CAR");
        arrayList.add("VAN");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Name = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + Name,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        dateText=findViewById(R.id.fm_date);
        dateText.setInputType(InputType.TYPE_NULL);
        dateText.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                final int year = cldr.get(Calendar.YEAR);
                //date picker Dialog
                picker = new DatePickerDialog(PlaceAppointmentForm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


        //Time Picker
        TimeText = findViewById(R.id.fm_Time);
        TimeText.setInputType(InputType.TYPE_NULL);
        TimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Timecldr = Calendar.getInstance();
                int hours = Timecldr.get(Calendar.HOUR_OF_DAY);
                int minutes = Timecldr.get(Calendar.MINUTE);

                Tpicker  = new TimePickerDialog(PlaceAppointmentForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int hours, int minutes) {
                        TimeText.setText(hours + ":" + minutes );
                    }
                },hours,minutes,true);
                Tpicker.show();
            }
        });

        btn_addTypeDB = findViewById(R.id.cusplaceApp);

        btn_addTypeDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAppointment();

            }

            private void createAppointment() {
                String vehicleType = spinner.getSelectedItem().toString();
                String date = dateText.getText().toString();
                String time = TimeText.getText().toString();


                if(TextUtils.isEmpty(vehicleType)){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Please select Vehicle Type", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }

                else if(TextUtils.isEmpty(date)){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Please select Vehicle Type", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }

                else if(TextUtils.isEmpty(time)){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Please select Vehicle Type", Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }

                else{
                    loadingBar.setTitle("Create Appointment");
                    loadingBar.setMessage("Creating Appointment");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    validateAppointment(date, time, vehicleType);

                }

            }

            private void validateAppointment(final String date, final String time, final String vehicleType) {
                final DatabaseReference ref;
                ref = FirebaseDatabase.getInstance().getReference();
                final String key = "A"+date.replace("/","")+"_"+time ;

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!(dataSnapshot.child("ProgressAppointments").child(key).exists())){
                            HashMap<String, Object> appdata = new HashMap<>();
                            appdata.put("Date",date);
                            appdata.put("Time",time);
                            appdata.put("CarWashType", carWashTypeVal);
//                            SessionReference =
//                                    FirebaseDatabase.getInstance().getReference().child(Prevalent.currentOnlineUser.getPhone());
                            appdata.put("C_Name", logged);


                            ref.child("ProgressAppointments").child(key).updateChildren(appdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(PlaceAppointmentForm.this, "Appointment Placed Successfully", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(PlaceAppointmentForm.this, "Error", Toast.LENGTH_LONG).show();
                                    }
                                    loadingBar.dismiss();

                                    Intent intent =  new Intent(PlaceAppointmentForm.this, AppointmentSummary.class);
                                    intent.putExtra("washType",carWashTypeVal);
                                    intent.putExtra("date",date);
                                    intent.putExtra("time",time);
                                    intent.putExtra("vehicleType",vehicleType);
                                    intent.putExtra("cno",logged);
                                    intent.putExtra("cType",carWashTypeVal);

                                    startActivity(intent);
                                }
                            });



                        }
                        else{

                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(!(snapshot.child("ClashAppointments").child(key).exists())){
                                        HashMap<String, Object> appdata = new HashMap<>();
                                        appdata.put("Date",date);
                                        appdata.put("Time",time);
                                        appdata.put("CarWashType", carWashTypeVal);
                                        appdata.put("C_Name", logged);
                                        appdata.put("Key", key);

                                        ref.child("ClashAppointments").child(key).updateChildren(appdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(PlaceAppointmentForm.this, "We already have an Appointment on that time", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(PlaceAppointmentForm.this, "Please wait for Approval", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    Toast.makeText(PlaceAppointmentForm.this, "Error", Toast.LENGTH_LONG).show();
                                                }
                                                loadingBar.dismiss();

                                                Intent intent =  new Intent(PlaceAppointmentForm.this, AppointmentSummary.class);
                                                intent.putExtra("washType",carWashTypeVal);
                                                intent.putExtra("date",date);
                                                intent.putExtra("time",time);
                                                intent.putExtra("vehicleType",vehicleType);
                                                intent.putExtra("cno",logged);
                                                intent.putExtra("cType",carWashTypeVal);

                                                startActivity(intent);
                                                loadingBar.dismiss();
                                            }
                                        });



                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }



        });

        loadingBar.dismiss();
    }
}
