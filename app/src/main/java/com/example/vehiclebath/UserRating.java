package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRating extends AppCompatActivity {

    private RatingBar userRating;
    DatabaseReference Rootref;
    int myRatings = 0;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_rating);

        userRating = (RatingBar)findViewById(R.id.ratingBar);

        loadingBar = new ProgressDialog(this);

        insertRating();
    }

    private void insertRating()
    {



        Rootref = FirebaseDatabase.getInstance().getReference().child("Ratings");

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userRating.setVisibility(View.VISIBLE);
                userRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


                        //------------------Rating Toast Message-------------------------//
                        int rating = (int)v;
                        String message = null;


                        myRatings = (int) ratingBar.getRating();

                        switch(rating){
                            case 1:
                                message = "Your rating is 1.0";

                                break;
                            case 2:
                                message = "Your rating is 2.0";
                                break;
                            case 3:
                                message = "Your rating is 3.0";
                                break;
                            case 4:
                                message = "Your rating is 4.0";
                                break;
                            case 5:
                                message = "Your rating is 5.0";
                                break;

                        }
                        loadingBar.setTitle("Submitting your rating");
                        loadingBar.setMessage("Thank you for rating us.......");
                        loadingBar.setCanceledOnTouchOutside(false);
                        loadingBar.show();
                        loadingBar.dismiss();
                        Toast toast = Toast.makeText(UserRating.this,message,Toast.LENGTH_LONG);
                        Toast.makeText(UserRating.this, "Thank you for rating us", Toast.LENGTH_SHORT).show();
                        toast.show();
                        //toast.setGravity(Gravity.CENTER,0,-150);
                        //------------------Rating Toast Message-------------------------//
                        Rootref.push().setValue(v);

                        Intent intent = new Intent(UserRating.this, HomeUserActivity.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}