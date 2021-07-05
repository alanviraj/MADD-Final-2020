package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.Proxy;

public class viewCarWash extends AppCompatActivity {

    private String TypeName, logged;
    private TextView carWashTypeName, TypePrice, TypeDescription;
    private Button btnSearch;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car_wash);

        logged = getIntent().getStringExtra("logged");

        TypeName = getIntent().getStringExtra("TypeName");

        carWashTypeName = (TextView)findViewById(R.id.carWashTypeName);
        TypePrice = (TextView)findViewById(R.id.TypePrice);
        TypeDescription = (TextView)findViewById(R.id.TypeDescription);

        btnSearch = findViewById(R.id.btnPlaceAppointment);

        TypeDisplay(TypeName);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewCarWash.this, PlaceAppointmentForm.class);
                intent.putExtra("Type", TypeName);
                intent.putExtra("logged", logged);
                startActivity(intent);
            }
        });

    }

    private void TypeDisplay(String typeName) {
        ref = FirebaseDatabase.getInstance().getReference().child("CarWashType");
        ref.child(typeName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    CarWashType carWashType = snapshot.getValue(CarWashType.class);
                    carWashTypeName.setText(carWashType.getTypeName());
                    TypePrice.setText(carWashType.getTypePrice());
                    TypeDescription.setText(carWashType.getTypeDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}