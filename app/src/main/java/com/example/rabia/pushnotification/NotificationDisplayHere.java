package com.example.rabia.pushnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationDisplayHere extends AppCompatActivity {
    TextView displayNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_display_here);
        String  datamsg=getIntent().getStringExtra("message");
        String  datafrom=getIntent().getStringExtra("from_email");
        displayNotification=findViewById(R.id.displayNotification);
        displayNotification.setText("message  "+datamsg + " from " + datafrom);
    }
}
