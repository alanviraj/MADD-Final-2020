package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Rating_organization extends AppCompatActivity {

    RatingBar ratingStars;
    private Button button;
    int myRatings = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_organization);

        button = (Button) findViewById(R.id.btnNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        ratingStars = findViewById(R.id.rateStrBar);
        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
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

                Toast toast = Toast.makeText(Rating_organization.this,message,Toast.LENGTH_LONG);
                toast.show();
                toast.setGravity(Gravity.CENTER,0,-250);


            }
        });


    }

    public void openDialog(){
        Dialog3 dialog3 = new Dialog3();
        dialog3.show(getSupportFragmentManager(),"Example Dialogue");
    }
}