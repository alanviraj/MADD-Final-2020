package com.example.vehiclebath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vehiclebath.Model.DecimalUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminRating extends AppCompatActivity {

    private RatingBar aRatingBar;
    private TextView tvRate;
    private TextView totServices;
    private TextView tv_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_rating);

        aRatingBar = (RatingBar)findViewById(R.id.ratingBarADD);
        tvRate = (TextView)findViewById(R.id.adminRate);
        totServices = (TextView)findViewById(R.id.totServices);
        tv_comment = (TextView)findViewById(R.id.tv_comment);
        getRatings();
    }

    private void getRatings() {

        DatabaseReference Rootref = FirebaseDatabase.getInstance().getReference().child("Ratings");
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount()>0)
                {
                    Toast.makeText(AdminRating.this, "Updated Ratings", Toast.LENGTH_SHORT).show();
                    int ratingSum = 0;
                    double ratingsTotal = 0;
                    double ratingAvg = 0;
                    for (DataSnapshot child:snapshot.getChildren())
                    {
                        ratingSum = ratingSum + Integer.valueOf(child.getValue().toString());
                        ratingsTotal++;
                    }
                    if(ratingsTotal!=0)
                    {
                        ratingAvg = calculateAvereage(ratingSum,ratingsTotal);
                       // ratingAvg = ratingSum / ratingsTotal;
                        aRatingBar.setRating((float) ratingAvg);


                        double roundedNumber = DecimalUtils.round(ratingAvg, 1);

                        tvRate.setText(roundedNumber+" Percent");

                        totServices.setText(Integer.toString((int) ratingsTotal)+" Services");

                        if(roundedNumber >=1 && roundedNumber < 2 )
                        {
                            tv_comment.setText("Poor !! Have to improve");
                        }else if(roundedNumber >= 2 && roundedNumber < 3)
                        {
                            tv_comment.setText("Fair service..should improve");
                        }else if(roundedNumber >= 3 && roundedNumber < 4)
                        {
                            tv_comment.setText("Can improve");
                        }else if (roundedNumber >=4 && roundedNumber < 5)
                        {
                            tv_comment.setText("Good service.. Can improve");
                        }else
                        {
                            tv_comment.setText("Excellent service KEEP IT UP!!!");
                        }

//                        if(roundedNumber == 5)
//                        {
//                            tv_comment.setText("Poor !! Have to improve");
//                        }else if(roundedNumber < 5 && roundedNumber >=4 )
//                        {
//                            tv_comment.setText("Fair service..should improve");
//                        }else if(roundedNumber < 4 && roundedNumber >= 3)
//                        {
//                            tv_comment.setText("Can improve");
//                        }else if(roundedNumber < 3 && roundedNumber >=2 )
//                        {
//                            tv_comment.setText("Good service.. Can improve");
//                        }else if(roundedNumber < 2 && roundedNumber >= 1)
//                        {
//                            tv_comment.setText("Excellent service");
//                        }else
//                        {
//                            tv_comment.setText("Good");
//                        }
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public double calculateAvereage(int ratingSum, double ratingsTotal) {
        return ratingSum / ratingsTotal;
    }


}