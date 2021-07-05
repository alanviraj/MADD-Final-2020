package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.Prevalent1.Prevalent;
import com.example.vehiclebath.model1.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginNew extends AppCompatActivity {

    private EditText inputnumber,inputpassword;
    private  Button loginButton;
    private ProgressDialog loadingBar;
    private TextView AdminLink, NotAdminLink, ForgetPasswordLink;
    private String parentDBName = "Users";
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        loginButton = (Button) findViewById(R.id.btnlogin);
        inputnumber = (EditText) findViewById(R.id.enter_log_num);
        inputpassword = (EditText) findViewById(R.id.enter_log_pwd);
        AdminLink = (TextView) findViewById(R.id.am_admin);
        NotAdminLink = (TextView) findViewById(R.id.not_admin);
        ForgetPasswordLink = (TextView) findViewById(R.id.for_pwd);
        loadingBar = new ProgressDialog(this);
        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chk);
        Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotAdminLink.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });
        NotAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotAdminLink.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });
        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, VerifyUserPwd.class));
            }
        });
    }
    private void LoginUser() {
        String number = inputnumber.getText().toString();
        String password = inputpassword.getText().toString();
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this,"Please Enter Number",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessToAccount(number,password);
        }
    }
    private void AllowAccessToAccount(final String number, final String password) {
        if (chkBoxRememberMe.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, number);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(parentDBName).child(number).exists()) {
                    Users usersData = snapshot.child(parentDBName).child(number).getValue(Users.class);
                    if (usersData.getPhone().equals(number)) {
                        if (usersData.getPassword().equals(password)) {
                            if (parentDBName.equals("Admins")) {
                                Toast.makeText(LoginNew.this,"Your are logged in!!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginNew.this,admin_main.class);
                                startActivity(intent);
                            }
                            else if (parentDBName.equals("Users")) {
                                Toast.makeText(LoginNew.this,"Your are logged in!!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginNew.this,HomeUserActivity.class);
                                intent.putExtra("logged", number);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LoginNew.this,"Password is incorrect!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(LoginNew.this,"Account with this "+number+ " do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}