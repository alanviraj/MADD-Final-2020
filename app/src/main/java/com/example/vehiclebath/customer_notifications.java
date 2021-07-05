package com.example.vehiclebath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class customer_notifications extends AppCompatActivity {

    Button btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_notifications);

        btnNotification = findViewById(R.id.btnNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messsage1 = "You declined the request before allocated time slot at Viha washers";
                String messsage2 = "You sent a request to Viha washers";
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        customer_notifications.this
                )
                        .setSmallIcon(R.drawable.logo1)
                        .setContentTitle("Vehicle Bath")
                        .setContentText(messsage1)
                        .setContentText(messsage2)
                        .setAutoCancel(true);

                Intent intent = new Intent(customer_notifications.this,customer_notifications.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",messsage1);
                intent.putExtra("message",messsage2);

                PendingIntent pendingIntent = PendingIntent.getActivity(customer_notifications.this,
                        0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager)getSystemService(
                        Context.NOTIFICATION_SERVICE
                );
                notificationManager.notify(0,builder.build());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}