package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.Model.Users;
import com.example.vehiclebath.Model.UsersReport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OrganizationReport_or_Activate_Activity extends AppCompatActivity {


    private static final String CHANNEL_ID = "channel_id01";
    private static final int NOTIFICATION_ID = 1;
    private TextView name_OR_activate,phone_OR_activate,password_OR_activate,email_OR_activate;
    private Button organization_OR_activate;
    private String userPhone ="";
    private ProgressDialog loadingBar;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_report_or__activate_);



        userPhone = getIntent().getStringExtra("UserPhone");

        name_OR_activate = (TextView) findViewById(R.id.user_name_OR_activate);
        phone_OR_activate = (TextView) findViewById(R.id.user_phone_OR_activate);
        password_OR_activate = (TextView) findViewById(R.id.user_password_OR_activate);
        email_OR_activate = (TextView) findViewById(R.id.user_email_OR_activate);

        organization_OR_activate = (Button) findViewById(R.id.btnReportSubmit_OR_activate);

        loadingBar = new ProgressDialog(this);

        getUserDetailsToDisplay(userPhone);


        organization_OR_activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlertDilague();
            }
        });



    }

    private void openAlertDilague() {
        //-------------------------------Alert dialogue--------------------------------------//
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                //set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
                //set title
                .setTitle("Are you sure you want to activate User")
                //set message
                .setMessage("Click yes to activate")
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        CreateReport();
                        DisplayNotification();

                        finish();

                    }
                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Intent intent = new Intent(OrganizationReport_or_Activate_Activity.this,CustomerReportedActivity.class);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(),"You selected No",Toast.LENGTH_LONG).show();
                    }
                })
                .show();

        //-------------------------------Alert dialogue--------------------------------------//


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void DisplayNotification() {

        //-------------------------------Notification--------------------------------------//

        createNotificationChannel();

        //Creating notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        //icon
        builder.setSmallIcon(R.drawable.logo1);
        //title
        builder.setContentTitle("Activation Successfull");
        //description
        builder.setContentText("You have activated a User. User details are successfully added activated ");
        //set priority
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //notification manager
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ECLAIR_0_1)
        {
            CharSequence name = "My Notification";
            String description  = "My Notification description";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }
    //-------------------------------Notification--------------------------------------//
    private void CreateReport() {
        String userName =name_OR_activate.getText().toString();
        String userPhone =phone_OR_activate.getText().toString();
        String userPassword =password_OR_activate.getText().toString();
        String userEmail =email_OR_activate.getText().toString();


        loadingBar.setTitle("Report Submission Progressing");
        loadingBar.setMessage("Please wait we are checking for you credentials");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        validate(userName,userPhone,userPassword,userEmail);

    }


    private void validate(final String userName,final  String userPhone,final  String userPassword,final  String userEmail)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("Users").child(userPhone).exists()))
                {
                    HashMap<String,Object> userDataMap = new HashMap<>();

                    userDataMap.put("Phone",userPhone);
                    userDataMap.put("Password",userPassword);
                    userDataMap.put("Name",userName);
                    userDataMap.put("Email",userEmail);

                    RootRef.child("Users").child(userPhone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(OrganizationReport_or_Activate_Activity.this, "Report has been submitted successfully", Toast.LENGTH_SHORT).show();

                                        //Delete user from UsersReport
                                        RootRef.child("UsersReport").child(userPhone).removeValue();

                                         Intent intent = new Intent(OrganizationReport_or_Activate_Activity.this,CustomerReportedActivity.class);
                                         startActivity(intent);

                                    }
                                    else{

                                        Toast.makeText(OrganizationReport_or_Activate_Activity.this, "Network Error : Please try again after some time", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(OrganizationReport_or_Activate_Activity.this,CustomerReportedActivity.class);
                                        startActivity(intent);
                                    }
                                   // loadingBar.dismiss();
                                }
                            });

                }
                else
                {
                    Toast.makeText(OrganizationReport_or_Activate_Activity.this, "Already there is a report been submitted on this number "+userPhone+" before", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();


                    Intent intent = new Intent(OrganizationReport_or_Activate_Activity.this,CustomerReportedActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getUserDetailsToDisplay(final String userPhone)
    {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("UsersReport");

        userRef.child(userPhone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    UsersReport usersReport = snapshot.getValue(UsersReport.class);
                    name_OR_activate.setText(usersReport.getName());
                    phone_OR_activate.setText(usersReport.getPhone());
                    password_OR_activate.setText(usersReport.getPassword());
                    email_OR_activate.setText(usersReport.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        userRef.child(userPhone).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists())
//                {
//                    UsersReport usersReport = dataSnapshot.getValue(UsersReport.class);
//                    name_OR_activate.setText(usersReport.getName());
//                    phone_OR_activate.setText(usersReport.getPhone());
//                    password_OR_activate.setText(usersReport.getPassword());
//                    email_OR_activate.setText(usersReport.getEmail());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("");
//        Query checkUser = reference.orderByChild("Phone").equalTo(userPhone);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if(snapshot.exists())
//                {
//                    UsersReport usersReport = snapshot.getValue(UsersReport.class);
//                    name_OR_activate.setText(usersReport.getName());
//                    phone_OR_activate.setText(usersReport.getPhone());
//                    password_OR_activate.setText(usersReport.getPassword());
//                    email_OR_activate.setText(usersReport.getEmail());
//                }

                /*
                *
                * if (dataSnapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        username.setError(null);
                        username.setErrorEnabled(false);
                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);
                        startActivity(intent);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                } */

            }
























    }
