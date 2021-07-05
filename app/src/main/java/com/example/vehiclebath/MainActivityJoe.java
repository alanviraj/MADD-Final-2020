package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vehiclebath.Prevalent1.Prevalent;
import com.example.vehiclebath.model1.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivityJoe extends AppCompatActivity {

    private Button loginMain_btn, join_btn;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_joe);

        loginMain_btn = (Button) findViewById(R.id.login_main);
        join_btn = (Button) findViewById(R.id.join_now);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginMain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityJoe.this, LoginNew.class);
                startActivity(intent);
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityJoe.this, RegisterUser.class);
                startActivity(intent);
            }
        });

        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey,UserPasswordKey);

                loadingBar.setTitle("Already Logged In");
                loadingBar.setMessage("Please wait..");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

            }
        }
    }

    private void AllowAccess(final String number, final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(number).exists()) {
                    Users usersData = snapshot.child("Users").child(number).getValue(Users.class);

                    if (usersData.getPhone().equals(number)) {

                        if (usersData.getPassword().equals(password)) {
                            Toast.makeText(MainActivityJoe.this,"Your are logged in!!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(MainActivityJoe.this,UserHomePge.class);
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(intent);

                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivityJoe.this,"Password is incorrect!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                else {
                    Toast.makeText(MainActivityJoe.this,"Account with this "+number+ " do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}